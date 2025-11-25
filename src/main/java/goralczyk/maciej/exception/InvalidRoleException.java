package goralczyk.maciej.exception;

public class InvalidRoleException extends SecurityException{
    public InvalidRoleException() {
    }
    public InvalidRoleException(String s) {
        super(s);
    }
    public InvalidRoleException(String message, Throwable cause) {
        super(message, cause);
    }
    public InvalidRoleException(Throwable cause) {
        super(cause);
    }
}
