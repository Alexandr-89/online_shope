package by.overone.online_store1.dao.impl;

import by.overone.online_store1.dao.UserDAO;
import by.overone.online_store1.dao.connectionPool.ConnectionPool;
import by.overone.online_store1.dao.connectionPool.connectionException.ConnectionException;
import by.overone.online_store1.dao.connectionPool.connectionException.ConnectionFullPoloException;
import by.overone.online_store1.dao.exception.DAOException;
import by.overone.online_store1.dao.exception.DAOExistException;
import by.overone.online_store1.dao.exception.UserDAONotFoundException;
import by.overone.online_store1.dto.UserRegistrationDTO;
import by.overone.online_store1.model.Role;
import by.overone.online_store1.model.Status;
import by.overone.online_store1.model.User;
import by.overone.online_store1.util.constant.Constant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class UserDAOImpl implements UserDAO {


    ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
    String dbUrl = resourceBundle.getString("dbUrl");
    String dbUser = resourceBundle.getString("dbUser");
    String dbPassword = resourceBundle.getString("dbPassword");


     ConnectionPool connectionPool = new ConnectionPool(dbUrl, dbUser, dbPassword, 5);
     Connection connection = null;

    private final static String GET_USERS_QUERY = "SELECT * FROM users WHERE user_status=?";
    private final static String GET_USER_BY_ID_QUERY = "SELECT * FROM users WHERE user_id=?";
    private final static String ADD_USER_QUERY = "INSERT INTO users VALUE(0,?,?,?)";
    private final static String ADD_USER_DETAILS_ID_QUERY = "INSERT INTO users_details(users_user_id) VALUE(?)";


//    static {
//        try {
//            String url = "ADD_USER_DETAILS_ID_QUERY";
//            String dbUser = "root";
//            String password = "root";
//            connection = DriverManager.getConnection(url, dbUser, password);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }


    @Override
    public List<User> getUsersByStatus(Status status) throws DAOException, ConnectionFullPoloException, SQLException, ConnectionException {
        List<User> users;
        connection = connectionPool.getConnection();
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(GET_USERS_QUERY);
            preparedStatement.setString(1,status.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            users = new ArrayList<>();
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong(Constant.ID));
                user.setLogin(resultSet.getString(Constant.LOGIN));
                user.setPassword(resultSet.getString(Constant.PASSWORD));
                user.setEmail(resultSet.getString(Constant.EMAIL));
                user.setRole(Role.valueOf(resultSet.getString(Constant.ROLE).toUpperCase(Locale.ROOT)));
                user.setStatus(Status.valueOf(resultSet.getString(Constant.STATUS).toUpperCase(Locale.ROOT)));
                users.add(user);
            }
        }catch (SQLException e){
            throw new DAOException("Not connection");
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public User getUserById(long id) throws DAOException, UserDAONotFoundException {
        User user;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_ID_QUERY);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()){
                throw new UserDAONotFoundException("User with id "+id+" not found");
            }
            user = new User();
            while (resultSet.next()){
                user.setId(resultSet.getLong(Constant.ID));
                user.setLogin(resultSet.getString(Constant.LOGIN));
                user.setPassword(resultSet.getString(Constant.PASSWORD));
                user.setEmail(resultSet.getString(Constant.EMAIL));
                user.setRole(Role.valueOf(resultSet.getString(Constant.ROLE).toUpperCase(Locale.ROOT)));
                user.setStatus(Status.valueOf(resultSet.getString(Constant.STATUS).toUpperCase(Locale.ROOT)));
            }
        }catch (SQLException e){
            throw new DAOException("Not connection");
        }
        return user;
    }


    @Override
    public UserRegistrationDTO addUser(UserRegistrationDTO user) throws DAOException, DAOExistException {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, Role.CUSTOMER.toString());
            preparedStatement.setString(5, Status.ACTIVE.toString());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }
            preparedStatement = connection.prepareStatement(ADD_USER_DETAILS_ID_QUERY);
            preparedStatement.setLong(1, user.getId());
            preparedStatement.executeUpdate();
            connection.commit();
        }catch (SQLIntegrityConstraintViolationException ex){
            throw new DAOExistException("Duplicate user", ex);
        }catch (SQLException e){
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new DAOException("Not connection");
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }
}
