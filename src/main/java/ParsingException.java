public class ParsingException extends Exception{
    private final String message;

    ParsingException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
