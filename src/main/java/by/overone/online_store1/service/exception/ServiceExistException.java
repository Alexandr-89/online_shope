package by.overone.online_store1.service.exception;

public class ServiceExistException extends Exception{

    public ServiceExistException() {
        super();
    }

    public ServiceExistException(String message) {
        super(message);
    }

    public ServiceExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
