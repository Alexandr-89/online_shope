package by.overone.online_store1.dao.connectionPool.connectionException;

public class ConnectionFullPoloException extends Exception{

    public ConnectionFullPoloException() {
        super();
    }

    public ConnectionFullPoloException(String message) {
        super(message);
    }

    public ConnectionFullPoloException(String message, Throwable cause) {
        super(message, cause);
    }
}
