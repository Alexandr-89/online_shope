package by.overone.online_store1.dao.connectionPool.connectionException;

public class ConnectionException extends Exception{

    public ConnectionException() {
        super();
    }

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
