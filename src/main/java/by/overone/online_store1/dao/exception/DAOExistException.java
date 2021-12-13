package by.overone.online_store1.dao.exception;

public class DAOExistException extends Exception{

    public DAOExistException() {
        super();
    }

    public DAOExistException(String message) {
        super(message);
    }

    public DAOExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
