package exception;

public class CorruptedEventDataException extends RuntimeException {

    public CorruptedEventDataException() {
        super("CorruptedEventDataException: The event data is corrupted, event skipped");
    }
}
