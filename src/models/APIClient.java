package models;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class APIClient {
    private final String _APIKey;
    private final String _APIURL = "https://airapi.airly.eu";
    private final String _APIMapPointURL = "/v1/mapPoint/measurements";
    private final String _APISensorURL = "/v1/nearestSensor/measurements";

    public APIClient(String _APIKey) {
        this._APIKey = _APIKey;
    }


    private enum API_PARAM {
        LAT, LONG, SENSOR_ID, API_KEY;

        public String toString(String param) {
            return this.toString() + param;
        }

        public String toString() {
            switch (this) {
                case LAT:
                    return "latitude=";
                case LONG:
                    return "longitude=";
                case SENSOR_ID:
                    return "sensorId=";
                case API_KEY:
                    return "apikey=";
                default:
                    return null;
            }
        }
    }

    /*
        public void test() throws URISyntaxException {
            URI address = new URI(_APIURL + _APIMapPointURL + "?" + API_PARAM.LAT.toString("50.06201") + "&" + API_PARAM.LONG.toString("19.94098") + "&" + API_PARAM.API_KEY.toString(this._APIKey));
            System.out.println(address.toString());
            AirQualityContainer container = null;
            try {
                container = getMeasurementsForPointOnMap("50.06201", "19.94098");
            } catch (IOException e) {
                System.out.println("EXCEPTION OCCURED");
                e.printStackTrace();
            }
            System.out.println(container.getCurrentMeasurements().getAirQualityIndex());
            System.out.println(container.getHistory().get(0).getMeasurements().getAirQualityIndex());
        }
    */
    public AirQualityContainer getMeasurementsForPointOnMap(String latitude, String longitude) throws IOException {
        return this.getMeasurementsForPointOnMap(latitude, longitude, false);
    }

    public AirQualityContainer getMeasurementsForPointOnMap(String latitude, String longitude, Boolean interpolated) throws IOException {
        AirQuality sensorData = getNearestSensorData(latitude, longitude);
        if (sensorData.getId() == null) {
            throw new NullPointerException("No sensor found nearby");
        }

        String response = sendGET(buildURL(this._APIMapPointURL, new String[]{API_PARAM.LAT.toString(latitude), API_PARAM.LONG.toString(longitude), API_PARAM.SENSOR_ID.toString(sensorData.getId().toString())}));
        AirQualityContainer pointData = new Gson().fromJson(response, AirQualityContainer.class);

        pointData.getCurrentMeasurements().merge(sensorData);

        return pointData;
    }

    public AirQualityContainer getMeasurementsForSensor(String sensorId) throws IOException {
        String response = sendGET(buildURL(this._APISensorURL, new String[]{API_PARAM.SENSOR_ID.toString(sensorId)}));
        return new Gson().fromJson(response, AirQualityContainer.class);
    }

    private AirQuality getNearestSensorData(String latitude, String longitude) throws IOException {
        String response = sendGET(buildURL(this._APISensorURL, new String[]{API_PARAM.LAT.toString(latitude), API_PARAM.LONG.toString(longitude)}));
        return new Gson().fromJson(response, AirQuality.class);
    }

    private URL buildURL(String path, String[] params) throws MalformedURLException {

        return new URL(this._APIURL + path + addParams(params));
    }

    private String addParams(String[] params) {
        StringBuilder _URI = new StringBuilder();
        _URI.append("?");

        for (String param : params) {
            _URI.append(param);
            _URI.append("&");
        }
        _URI.append(API_PARAM.API_KEY.toString(this._APIKey));

        return _URI.toString();
    }

    private String sendGET(URL query) throws IOException {

        HttpURLConnection con = (HttpURLConnection) query.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        return content.toString();
    }

}
