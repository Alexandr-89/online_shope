package by.overone.online_store1.service.impl;

import by.overone.online_store1.dao.UserDAO;
import by.overone.online_store1.dao.connectionPool.connectionException.ConnectionException;
import by.overone.online_store1.dao.connectionPool.connectionException.ConnectionFullPoolException;
import by.overone.online_store1.dao.exception.DAOException;
import by.overone.online_store1.dao.exception.DAOExistException;
import by.overone.online_store1.dao.exception.UserDAONotFoundException;
import by.overone.online_store1.dao.impl.UserDAOImpl;
import by.overone.online_store1.dto.UserAllInfoDTO;
import by.overone.online_store1.dto.UserDTO;
import by.overone.online_store1.dto.UserDateilsDTO;
import by.overone.online_store1.dto.UserRegistrationDTO;
import by.overone.online_store1.model.Status;
import by.overone.online_store1.model.User;
import by.overone.online_store1.service.UserService;
import by.overone.online_store1.service.exception.ServiceException;
import by.overone.online_store1.service.exception.ServiceNotFounException;
import by.overone.online_store1.validator.UserValidator;
import by.overone.online_store1.validator.exception.ValidatorException;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    public UserDAO userDAO = new UserDAOImpl();

    @Override
    public List<UserDTO> getAllActiveUsers() throws ServiceException, ConnectionFullPoolException, SQLException, ConnectionException {
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
        } catch (ConnectionFullPoolException | ConnectionException e) {
            e.printStackTrace();
        }
        return userDTOs;
    }


    @Override
    public boolean addUser(UserRegistrationDTO userRegistrationDTO) throws ServiceException {
        try {
            UserValidator.validateUserRegistrationDTO(userRegistrationDTO);
        } catch (ValidatorException e) {
            throw new ServiceException("incorrect data");
        }
        try {
            userDAO.addUser(userRegistrationDTO);
        } catch (DAOException | DAOExistException | ConnectionFullPoolException | ConnectionException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void addUserDetails(long id, UserDateilsDTO userDateilsDTO) throws ServiceException, ServiceNotFounException {
        getUserById(id);
        try {
            UserValidator.validateUserDateils(userDateilsDTO);
            userDAO.addUserDetails(id, userDateilsDTO);
        } catch (ValidatorException | ConnectionFullPoolException | SQLException | DAOException | ConnectionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(long id) throws ServiceException, ServiceNotFounException {
        getUserById(id);
        try {
            userDAO.deleteUser(id);
        } catch (UserDAONotFoundException e) {
            throw new ServiceException("error");
        }
    }

    @Override
    public UserAllInfoDTO getUserAllInfo(long id) throws ServiceException {
        UserAllInfoDTO userAllInfoDTO;
            try {
                userAllInfoDTO = userDAO.getUserAllInfo(id);
            } catch (DAOException e) {
                throw new ServiceException("service error");
            }
        return userAllInfoDTO;
    }
}
