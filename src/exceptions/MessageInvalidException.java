package exceptions;

public class MessageInvalidException extends RuntimeException {
    public MessageInvalidException() {
        super("Invalid Message");
    }
}
