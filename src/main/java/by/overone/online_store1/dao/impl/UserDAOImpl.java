package by.overone.online_store1.dao.impl;

import by.overone.online_store1.dao.UserDAO;
import by.overone.online_store1.dao.connectionPool.ConnectionPool;
import by.overone.online_store1.dao.connectionPool.connectionException.ConnectionException;
import by.overone.online_store1.dao.connectionPool.connectionException.ConnectionFullPoolException;
import by.overone.online_store1.dao.exception.DAOException;
import by.overone.online_store1.dao.exception.DAOExistException;
import by.overone.online_store1.dao.exception.UserDAONotFoundException;
import by.overone.online_store1.dto.UserAllInfoDTO;
import by.overone.online_store1.dto.UserDateilsDTO;
import by.overone.online_store1.dto.UserRegistrationDTO;
import by.overone.online_store1.model.Role;
import by.overone.online_store1.model.Status;
import by.overone.online_store1.model.User;
import by.overone.online_store1.util.constant.ConstantUser;

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
    private final static String ADD_USER_QUERY = "INSERT INTO users VALUE(0,?,?,?,?,?)";
    private final static String ADD_USER_DETAILS_ID_QUERY = "INSERT INTO users_details(users_user_id) VALUE(?)";
    private final static String ADD_USER_DETAILS_QUERY = "UPDATE users_details SET user_detail_name=?, " +
            "user_detail_surname=?, user_detail_address=?, user_detail_phone=? WHERE users_user_id=?";
    private final static String DELETE_USER_QUERY = "UPDATE users SET status=? WHERE user_id=?";
    private final static String GET_USER_ALL_DATA_QUERY = "SELECT*FROM users JOIN users_details ON " +
            "user_id=users_user_id WHERE user_id=?";
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
    public List<User> getUsersByStatus(Status status) throws DAOException, ConnectionFullPoolException, ConnectionException {
        List<User> users;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USERS_QUERY);
            preparedStatement.setString(1,status.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            users = new ArrayList<>();
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong(ConstantUser.ID));
                user.setLogin(resultSet.getString(ConstantUser.LOGIN));
                user.setPassword(resultSet.getString(ConstantUser.PASSWORD));
                user.setEmail(resultSet.getString(ConstantUser.EMAIL));
                user.setRole(Role.valueOf(resultSet.getString(ConstantUser.ROLE).toUpperCase(Locale.ROOT)));
                user.setStatus(Status.valueOf(resultSet.getString(ConstantUser.STATUS).toUpperCase(Locale.ROOT)));
                users.add(user);
            }
        }catch (SQLException e){
            throw new DAOException("Not connection");
        }finally {
            if (connection!=null){
                try {
                    connectionPool.returnConnection(connection);
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return users;
    }

    @Override
    public User getUserById(long id) throws DAOException, UserDAONotFoundException, ConnectionFullPoolException, ConnectionException {
        User user;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_ID_QUERY);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            user = new User();
            if (resultSet.next()){
                user.setId(resultSet.getLong(ConstantUser.ID));
                user.setLogin(resultSet.getString(ConstantUser.LOGIN));
                user.setPassword(resultSet.getString(ConstantUser.PASSWORD));
                user.setEmail(resultSet.getString(ConstantUser.EMAIL));
                user.setRole(Role.valueOf(resultSet.getString(ConstantUser.ROLE).toUpperCase(Locale.ROOT)));
                user.setStatus(Status.valueOf(resultSet.getString(ConstantUser.STATUS).toUpperCase(Locale.ROOT)));
            }else {
                throw new UserDAONotFoundException("net usera");
            }
        }catch (SQLException e){
            throw new DAOException("Not connection");
        }finally {
            if (connection!=null){
                try {
                    connectionPool.returnConnection(connection);
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return user;
    }


    @Override
    public UserRegistrationDTO addUser(UserRegistrationDTO user) throws DAOException, DAOExistException, ConnectionFullPoolException, ConnectionException {

        try {
            connection = connectionPool.getConnection();
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
            if (connection!=null){
                try {
                    connectionPool.returnConnection(connection);
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return user;
    }

    @Override
    public UserDateilsDTO addUserDetails(long id, UserDateilsDTO userDateilsDTO) throws ConnectionFullPoolException, ConnectionException, DAOException {
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER_DETAILS_QUERY);
            preparedStatement.setString(1, userDateilsDTO.getName());
            preparedStatement.setString(2, userDateilsDTO.getSurname());
            preparedStatement.setString(3, userDateilsDTO.getAddress());
            preparedStatement.setString(4, userDateilsDTO.getPhone());
            preparedStatement.setLong(5, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException("not connection");
        }finally {
            if (connection!=null){
                try {
                    connectionPool.returnConnection(connection);
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public boolean deleteUser(long id) throws UserDAONotFoundException {
        try {
            try {
                connection = connectionPool.getConnection();
            } catch (ConnectionFullPoolException e) {
                e.printStackTrace();
            } catch (ConnectionException e) {
                e.printStackTrace();
            }
            PreparedStatement preparedStatement=connection.prepareStatement(DELETE_USER_QUERY);
            preparedStatement.setString(1, Status.INACTIVE.toString());
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new UserDAONotFoundException("User not delete");
        }finally {
            if (connection!=null){
                try {
                    connectionPool.returnConnection(connection);
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    @Override
    public UserAllInfoDTO getUserAllInfo(long id) throws DAOException {
        UserAllInfoDTO userAllInfoDTO = new UserAllInfoDTO();
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_ALL_DATA_QUERY);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                userAllInfoDTO.setLogin(resultSet.getString(ConstantUser.LOGIN));
                userAllInfoDTO.setPassword(resultSet.getString(ConstantUser.PASSWORD));
                userAllInfoDTO.setEmail(resultSet.getString(ConstantUser.EMAIL));
                userAllInfoDTO.setRole(Role.valueOf(resultSet.getString(ConstantUser.ROLE).toUpperCase(Locale.ROOT)));
                userAllInfoDTO.setStatus(Status.valueOf(resultSet.getString(ConstantUser.STATUS).toUpperCase(Locale.ROOT)));
                userAllInfoDTO.setName(resultSet.getString(ConstantUser.NAME));
                userAllInfoDTO.setSurname(resultSet.getString(ConstantUser.SURNAME));
                userAllInfoDTO.setAddress(resultSet.getString(ConstantUser.ADDRESS));
                userAllInfoDTO.setPhone(resultSet.getString(ConstantUser.PHONE));
            }else {
                throw new DAOException(" error");
            }
        } catch (ConnectionFullPoolException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ConnectionException e) {
            e.printStackTrace();
        }finally {
            if (connection!=null){
                try {
                    connectionPool.returnConnection(connection);
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return userAllInfoDTO;
    }


}
