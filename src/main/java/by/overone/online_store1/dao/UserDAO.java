package by.overone.online_store1.dao;

import by.overone.online_store1.dao.exception.DAOException;
import by.overone.online_store1.dao.exception.UserDAONotFoundException;
import by.overone.online_store1.model.Status;
import by.overone.online_store1.model.User;

import java.util.List;

public interface UserDAO {

    List<User> getUsersByStatus(Status status) throws DAOException;
    User getUserById(long id) throws DAOException, UserDAONotFoundException;
}
