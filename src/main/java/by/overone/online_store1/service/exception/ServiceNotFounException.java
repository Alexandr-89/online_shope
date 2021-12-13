package by.overone.online_store1.service.exception;

public class ServiceNotFounException extends Exception{
    public ServiceNotFounException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceNotFounException(String message) {
        super(message);
    }
}
