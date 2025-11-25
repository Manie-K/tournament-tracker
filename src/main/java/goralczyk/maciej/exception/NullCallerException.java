package goralczyk.maciej.exception;

public class NullCallerException extends SecurityException{
    public NullCallerException() {
    }
    public NullCallerException(String s) {
        super(s);
    }
    public NullCallerException(String message, Throwable cause) {
        super(message, cause);
    }
    public NullCallerException(Throwable cause) {
        super(cause);
    }
}
