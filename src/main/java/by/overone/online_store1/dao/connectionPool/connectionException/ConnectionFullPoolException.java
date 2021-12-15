package by.overone.online_store1.dao.connectionPool.connectionException;

public class ConnectionFullPoolException extends Exception{

    public ConnectionFullPoolException() {
        super();
    }

    public ConnectionFullPoolException(String message) {
        super(message);
    }

    public ConnectionFullPoolException(String message, Throwable cause) {
        super(message, cause);
    }
}
