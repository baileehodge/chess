package ui;

public class UIException extends Exception {
    final private String message;

    public UIException(int statusCode, String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
