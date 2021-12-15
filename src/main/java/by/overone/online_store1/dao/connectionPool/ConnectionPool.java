package by.overone.online_store1.dao.connectionPool;

import by.overone.online_store1.dao.connectionPool.connectionException.ConnectionException;
import by.overone.online_store1.dao.connectionPool.connectionException.ConnectionFullPoolException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class ConnectionPool {

    private String databaceUrl;
    private String userName;
    private String userPassword;
    private int maxPoolSize = 10;
    private int connNum = 0;


    private static final String SQL_VERIFYCONN = "select 1";

    Stack<Connection> freePool = new Stack<>();
    Set<Connection> occupiedPool = new HashSet<>();

    public ConnectionPool(String databaceUrl, String userName, String userPassword, int maxPoolSize) {
        this.databaceUrl = databaceUrl;
        this.userName = userName;
        this.userPassword = userPassword;
        this.maxPoolSize = maxPoolSize;
    }

    public synchronized Connection getConnection() throws ConnectionFullPoolException, SQLException, ConnectionException {
        Connection connection = null;
        if (isFull()) {
            throw new ConnectionFullPoolException("The connection pool is full.");
        }
        connection = getConnectionFromPool();
        if (connection == null) {
            connection = createNewConnectionForPool();
        }
        connection = makeAvailable(connection);
        return connection;
    }


    public synchronized void returnConnection(Connection connection) throws SQLException {
        if (connection == null) {
            throw new NullPointerException();
        }
        if (!occupiedPool.remove(connection)) {
            throw new SQLException("The connection is returned already or isn't for this pool");
        }
        freePool.push(connection);
    }


    private synchronized boolean isFull() {
        return ((freePool.size() == 0) && (connNum >= maxPoolSize));
    }




    private Connection createNewConnectionForPool() throws ConnectionException {
        Connection connection = createNewConnection();
        connNum++;
        occupiedPool.add(connection);
        return connection;
    }


    private Connection createNewConnection() throws ConnectionException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(databaceUrl, userName, userPassword);
        } catch (SQLException e) {
            throw new ConnectionException("Not connection");
        }
        return connection;
    }


    private Connection getConnectionFromPool() {
        Connection connection = null;
        if (freePool.size() > 0) {
            connection = freePool.pop();
            occupiedPool.add(connection);
        }
        return connection;
    }



    private Connection makeAvailable(Connection connection) throws SQLException, ConnectionException {
    if (isConnectionAvailable(connection)) {
        return connection;
    }
    occupiedPool.remove(connection);
    connNum--;
    connection.close();
    connection=createNewConnection();
    occupiedPool.add(connection);
    connNum++;
    return connection;
}

    private boolean isConnectionAvailable(Connection connection){
        try (Statement st = connection.createStatement()){
            st.executeQuery(SQL_VERIFYCONN);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
