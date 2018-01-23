package models;

import java.lang.reflect.Field;
import java.util.Map;

public class AirQuality {
    private Double airQualityIndex;
    private Double pm25;
    private Double pm10;
    private Double pressure;
    private Double humidity;
    private Double temperature;
    private Integer id;
    private Integer pollutionLevel;
    private Map<String, String> address;

    public Double getAirQualityIndex() {
        return airQualityIndex;
    }

    public Double getPm25() {
        return pm25;
    }

    public Double getPm10() {
        return pm10;
    }

    public Double getPressure() {
        return pressure;
    }

    public Double getHumidity() {
        return humidity;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Integer getId() {
        return id;
    }

    public Integer getPollutionLevel() {
        return pollutionLevel;
    }

    public Map<String, String> getAddress() {
        return address;
    }

    public void merge(AirQuality that) {
        Field[] fields = AirQuality.class.getDeclaredFields();
        try {
            for (Field field : fields) {
                if (field.get(this) == null) {
                    field.set(this, field.get(that));
                }
            }
        } catch (IllegalAccessException e) {
        }
    }

    public boolean isNull() {
        return this.airQualityIndex == null;
    }
}
