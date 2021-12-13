package by.overone.online_store1.service;

import by.overone.online_store1.dto.UserDTO;
import by.overone.online_store1.dto.UserRegistrationDTO;
import by.overone.online_store1.service.exception.ServiceException;
import by.overone.online_store1.service.exception.ServiceNotFounException;

import java.util.List;

public interface UserService {

    List<UserDTO> getAllActiveUsers() throws ServiceException;
    UserDTO getUserById(long id) throws ServiceException, ServiceNotFounException;
    boolean addUser(UserRegistrationDTO userDTORegistration) throws ServiceException;
}
