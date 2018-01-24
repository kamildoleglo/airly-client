package views;

import models.AirQuality;
import models.AirQualityContainer;

import java.text.DateFormat;
import java.util.*;

public class AirQualityView {

    public void printAirQuality(AirQualityContainer data) {
        printAirQuality(data.getCurrentMeasurements());
    }

    private void printAirQuality(AirQuality data) {
        if (data.isNull()) return;
        String[] art = AsciiArtHelper.getAsciiArt(data.getPollutionLevel());
        AirQualityHelper.Pair pair = AirQualityHelper.getAirQualityData(data);
        int maxLength = 1;
        for (String line : art) {
            if (line.length() > maxLength) {
                maxLength = line.length();
            }
        }
        int i;
        for (i = 0; i < art.length && i < pair.getLength(); i++) {
            System.out.printf("%-" + maxLength + ".30s  %-18.18s %-18.30s%n", art[i], pair.getNames()[i], pair.getValues()[i]);
        }
        while (i < art.length) {
            System.out.printf("%-" + maxLength + ".30s  %-18.18s %-18.30s%n", art[i], "", "");
            i++;
        }
        while (i < pair.getLength()) {
            System.out.printf("%-" + maxLength + ".30s  %-18.18s %-18.30s%n", "", pair.getNames()[i], pair.getValues()[i]);
            i++;
        }
    }

    public void printAirQualityWithHistoricalData(AirQualityContainer data) {
        System.out.println("Aktualne dane: \r\n");

        printAirQuality(data.getCurrentMeasurements());
        DateFormat df = DateFormat.getDateTimeInstance();
        System.out.println();
        AirQualityHelper.printHashLine();
        System.out.println("Dane historyczne:");
        List<AirQualityContainer.HistoricalAirQualityContainer> historicalDataContainers = data.getHistory();
        Collections.reverse(historicalDataContainers);
        for (AirQualityContainer.HistoricalAirQualityContainer container : historicalDataContainers) {
            if (container.getMeasurements() == null) {
                continue;
            }
            AirQualityHelper.printHashLine();
            System.out.println("Dane od: " + df.format(container.getFromDateTime()));
            System.out.println("Dane do: " + df.format(container.getTillDateTime()) + "\r\n");
            printAirQuality(container.getMeasurements());
        }
    }

}
