package by.overone.online_store1.dao.impl;

import by.overone.online_store1.dao.UserDAO;
import by.overone.online_store1.dao.exception.DAOException;
import by.overone.online_store1.dao.exception.UserDAONotFoundException;
import by.overone.online_store1.model.Role;
import by.overone.online_store1.model.Status;
import by.overone.online_store1.model.User;
import by.overone.online_store1.util.constant.Constant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UserDAOImpl implements UserDAO {


    private static Connection connection;

    private final static String GET_USERS_QUERY = "SELECT * FROM users WHERE user_status=?";
    private final static String GET_USER_BY_ID_QUERY = "SELECT * FROM users WHERE user_id=?";


    static {
        try {
            String url = "jdbc:mysql://localhost:3306/online_store";
            String dbUser = "root";
            String password = "root";
            connection = DriverManager.getConnection(url, dbUser, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<User> getUsersByStatus(Status status) throws DAOException {
        List<User> users;
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
}
