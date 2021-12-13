package by.overone.online_store1.service.impl;

import by.overone.online_store1.dao.UserDAO;
import by.overone.online_store1.dao.exception.DAOException;
import by.overone.online_store1.dao.exception.UserDAONotFoundException;
import by.overone.online_store1.dao.impl.UserDAOImpl;
import by.overone.online_store1.dto.UserDTO;
import by.overone.online_store1.model.Status;
import by.overone.online_store1.model.User;
import by.overone.online_store1.service.UserService;
import by.overone.online_store1.service.exception.ServiceException;
import by.overone.online_store1.service.exception.ServiceNotFounException;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    public UserDAO userDAO = new UserDAOImpl();

    @Override
    public List<UserDTO> getAllActiveUsers() throws ServiceException {
        List<UserDTO> userDTOs;
        try {
            List<User> users = userDAO.getUsersByStatus(Status.ACTIVE);
            userDTOs = users.stream()
                    .map(user -> new UserDTO(user.getId(), user.getLogin(), user.getEmail(), user.getRole(), user.getStatus()))
                    .collect(Collectors.toList());

        }catch (DAOException e){
            throw new ServiceException("Not connection");
        }
        return userDTOs;
    }

    @Override
    public UserDTO getUserById(long id) throws ServiceException, ServiceNotFounException {
        UserDTO userDTOs = new UserDTO();
        try {
            User user =userDAO.getUserById(id);
            userDTOs.setId(user.getId());
            userDTOs.setLogin(user.getLogin());
            userDTOs.setEmail(user.getEmail());
            userDTOs.setRole(user.getRole());
            userDTOs.setStatus(user.getStatus());
        } catch (DAOException e) {
            throw new ServiceException("Not connection");
        } catch (UserDAONotFoundException ex) {
            throw new ServiceNotFounException("User with id "+id+" not found", ex);
        }
        return userDTOs;
    }
}
