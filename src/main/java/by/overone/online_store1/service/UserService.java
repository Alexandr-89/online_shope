package by.overone.online_store1.service;

import by.overone.online_store1.dao.connectionPool.connectionException.ConnectionException;
import by.overone.online_store1.dao.connectionPool.connectionException.ConnectionFullPoolException;
import by.overone.online_store1.dto.UserAllInfoDTO;
import by.overone.online_store1.dto.UserDTO;
import by.overone.online_store1.dto.UserDateilsDTO;
import by.overone.online_store1.dto.UserRegistrationDTO;
import by.overone.online_store1.service.exception.ServiceException;
import by.overone.online_store1.service.exception.ServiceNotFounException;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

    List<UserDTO> getAllActiveUsers() throws ServiceException, ConnectionFullPoolException, SQLException, ConnectionException;
    UserDTO getUserById(long id) throws ServiceException, ServiceNotFounException;
    boolean addUser(UserRegistrationDTO userDTORegistration) throws ServiceException;
    void addUserDetails(long id, UserDateilsDTO userDateilsDTO) throws ServiceException, ServiceNotFounException;
    void deleteUser(long id) throws ServiceException, ServiceNotFounException;
    UserAllInfoDTO getUserAllInfo(long id) throws ServiceException;
}
