package views;

import models.AirQuality;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AirQualityHelper {

    public static class Pair {
        private final String[] names;
        private final String[] values;

        public Pair(String[] names, String[] values) {
            this.names = names;
            this.values = values;
        }

        public String[] getNames() {
            return names;
        }

        public String[] getValues() {
            return values;
        }

        public Integer getLength() {
            return names.length;
        }
    }

    public static Pair getAirQualityData(AirQuality data) {
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> values = new ArrayList<String>();
        DecimalFormat decimal = new DecimalFormat("#");

        if (data.getPollutionLevel() != null) {
            names.add("Jakość powietrza: ");
            values.add(AirPollutionLevel.intToLevel(data.getPollutionLevel()).toString());
        }
        if (data.getAirQualityIndex() != null) {
            names.add("Indeks CAQI: ");
            values.add(decimal.format(data.getAirQualityIndex()));
        }
        if (data.getPm25() != null) {
            names.add("PM 2.5: ");
            values.add(decimal.format(data.getPm25()) + " μg/m3");
        }
        if (data.getPm10() != null) {
            names.add("PM 10: ");
            values.add(decimal.format(data.getPm10()) + " μg/m3");
        }
        if (data.getTemperature() != null) {
            names.add("Temperatura: ");
            values.add(decimal.format(data.getTemperature()) + " °C");
        }
        if (data.getPressure() != null) {
            names.add("Ciśnienie: ");
            values.add(decimal.format(data.getPressure() / 100) + " hPa");
        }
        if (data.getHumidity() != null) {
            names.add("Wilgotność: ");
            values.add(decimal.format(data.getHumidity()) + " %");
        }
        return new AirQualityHelper.Pair(names.toArray(new String[names.size()]), values.toArray(new String[values.size()]));
    }

    public static void printHashLine() {
        System.out.println("##############################################");
    }

    public enum AirPollutionLevel {
        NONE, VERY_LOW, LOW, MODERATE, HIGH, VERY_HIGH, EXTREMAL;

        public String toString() {
            switch (this) {
                case NONE:
                    return "Brak zanieczyszczeń";
                case VERY_LOW:
                    return "Bardzo dobra";
                case LOW:
                    return "Dobra";
                case MODERATE:
                    return "Średnia";
                case HIGH:
                    return "Zła";
                case VERY_HIGH:
                    return "Bardzo zła";
                case EXTREMAL:
                    return "Ja bym uciekał";
                default:
                    return "";
            }
        }

        public static AirPollutionLevel intToLevel(int pollutionLevel) {
            switch (pollutionLevel) {
                case 0:
                    return NONE;
                case 1:
                    return VERY_LOW;
                case 2:
                    return LOW;
                case 3:
                    return MODERATE;
                case 4:
                    return HIGH;
                case 5:
                    return VERY_HIGH;
                case 6:
                    return EXTREMAL;
                default:
                    return null;
            }
        }
    }
}
