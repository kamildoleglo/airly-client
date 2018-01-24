package controllers;

import models.APIClient;
import models.AirQualityContainer;
import views.AirQualityView;
import views.ErrorView;

import java.io.IOException;

import org.apache.commons.cli.*;


public class ApplicationController {
    public static void main(String[] args) {

        Options options = ApplicationController.programOptions();

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            new ErrorView(ErrorView.Type.MALFORMED_ARGS);
            formatter.printHelp("airly-client", options);
            System.exit(1);
            return;
        }


        String _API_key = cmd.hasOption("api-key") ? cmd.getOptionValue("api-key") : System.getenv("API_KEY");
        if (_API_key == null) {
            new ErrorView(ErrorView.Type.API_KEY_NOT_PRESENT);
            System.exit(1);
            return;
        }


        try {
            APIClient client = new APIClient(_API_key);
            AirQualityContainer data;

            if (cmd.hasOption("sensor-id")) {
                data = client.getMeasurementsForSensor(cmd.getOptionValue("sensor-id"));
                if (data.getCurrentMeasurements().isNull()) {
                    new ErrorView(ErrorView.Type.INCORRECT_SENSOR);
                    System.exit(1);
                    return;
                }
            } else if (cmd.hasOption("latitude") && cmd.hasOption("longitude")) {
                data = client.getMeasurementsForPointOnMap(cmd.getOptionValue("latitude"), cmd.getOptionValue("longitude"));
                if (data.getCurrentMeasurements().isNull()) {
                    new ErrorView(ErrorView.Type.INCORRECT_COORDS);
                    System.exit(1);
                    return;
                }
            } else {
                new ErrorView(ErrorView.Type.MALFORMED_ARGS);
                System.exit(1);
                return;
            }

            AirQualityView view = new AirQualityView();

            if (cmd.hasOption("history")) {
                view.printAirQualityWithHistoricalData(data);
            } else {
                view.printAirQuality(data);
            }

        } catch (IOException e) {
            new ErrorView(ErrorView.Type.NETWORK);
            System.exit(1);
        } catch (IllegalAccessException e) {
            new ErrorView(ErrorView.Type.MALFORMED_API_KEY);
            System.exit(1);
        } catch (NullPointerException e) {
            new ErrorView(ErrorView.Type.NO_SENSOR_FOUND);
            System.exit(1);
        } catch (IllegalArgumentException e) {
            new ErrorView(ErrorView.Type.INCORRECT_SENSOR);
            System.exit(1);
        }
        System.exit(0);
    }


    public static Options programOptions() {

        Options options = new Options();

        Option apiKey = new Option("a", "api-key", true, "API key");
        options.addOption(apiKey);

        Option latitude = new Option("lat", "latitude", true, "latitude");
        options.addOption(latitude);

        Option longitude = new Option("lon", "longitude", true, "longitude");
        options.addOption(longitude);

        Option sensor = new Option("s", "sensor-id", true, "specific sensor id");
        options.addOption(sensor);

        Option history = new Option("h", "history", false, "show historical measurements");
        options.addOption(history);
        return options;
    }
}
