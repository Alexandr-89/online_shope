package by.overone.online_store1.dao;

import by.overone.online_store1.dao.connectionPool.connectionException.ConnectionException;
import by.overone.online_store1.dao.connectionPool.connectionException.ConnectionFullPoloException;
import by.overone.online_store1.dao.exception.DAOException;
import by.overone.online_store1.dao.exception.DAOExistException;
import by.overone.online_store1.dao.exception.UserDAONotFoundException;
import by.overone.online_store1.dto.UserRegistrationDTO;
import by.overone.online_store1.model.Status;
import by.overone.online_store1.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {

    List<User> getUsersByStatus(Status status) throws DAOException, ConnectionFullPoloException, SQLException, ConnectionException;
    User getUserById(long id) throws DAOException, UserDAONotFoundException;
    UserRegistrationDTO addUser(UserRegistrationDTO user) throws DAOException, DAOExistException;
}
