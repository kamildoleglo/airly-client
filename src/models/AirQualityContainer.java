package models;

import java.util.Date;
import java.util.List;

public class AirQualityContainer {
    private AirQuality currentMeasurements;
    private List<HistoricalAirQualityContainer> history;

    public AirQuality getCurrentMeasurements() {
        return currentMeasurements;
    }

    public List<HistoricalAirQualityContainer> getHistory() {
        return history;
    }

    public class HistoricalAirQualityContainer {
        private Date fromDateTime;
        private Date toDateTime;
        private AirQuality measurements;

        public AirQuality getMeasurements() {
            return measurements;
        }
    }

}
