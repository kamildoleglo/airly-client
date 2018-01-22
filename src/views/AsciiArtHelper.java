package views;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class AsciiArtHelper {
    public static String[] getAsciiArt(int pollutionLevel) {
        String[] art;
        try (Stream<String> stream = Files.lines(Paths.get("src/assets/" + getFilename(pollutionLevel)))) {
            art = stream.toArray(String[]::new);

        } catch (IOException e) {
            return new String[]{""};
        }
        return art;
    }

    private static String getFilename(int pollutionLevel) {
        switch (pollutionLevel) {
            case 0:
                return "";
            case 1:
                return "";
            case 2:
                return "low";
            case 3:
                return "moderate";
            case 4:
                return "high";
            case 5:
                return "veryHigh";
            case 6:
                return "extremal";
            default:
                return "";

        }
    }
}
