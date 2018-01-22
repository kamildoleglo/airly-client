package views;

public class ErrorView {

    public ErrorView(Type errorType) {
        System.out.println(errorType.toString());
    }

    public enum Type {
        MALFORMED_API_KEY, API_KEY_NOT_PRESENT, NETWORK, MALFORMED_ARGS, INCORRECT_COORDS, INCORRECT_SENSOR;

        public String toString() {
            switch (this) {
                case MALFORMED_API_KEY:
                    return "Niepoprawny klucz API";
                case API_KEY_NOT_PRESENT:
                    return "Brak klucza API";
                case NETWORK:
                    return "Problem z siecią";
                case MALFORMED_ARGS:
                    return "Niepoprawne argumenty";
                case INCORRECT_COORDS:
                    return "Niepoprawne koordynaty";
                case INCORRECT_SENSOR:
                    return "Niepoprawny numer sensora";
                default:
                    return "";
            }
        }
    }
}
