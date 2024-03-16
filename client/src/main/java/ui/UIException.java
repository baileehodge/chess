package ui;

public class UIException extends Exception {
    final private int statusCode;
    final private String message;

    public UIException(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int StatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
