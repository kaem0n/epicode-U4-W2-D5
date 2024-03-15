package kaem0n.exceptions;

public class InvalidISBNCodeException extends Exception {
    public InvalidISBNCodeException() {
    }

    public InvalidISBNCodeException(String message) {
        super(message);
    }
}
