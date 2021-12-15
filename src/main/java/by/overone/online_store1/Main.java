package by.overone.online_store1;

import by.overone.online_store1.dao.UserDAO;
import by.overone.online_store1.dao.connectionPool.connectionException.ConnectionException;
import by.overone.online_store1.dao.connectionPool.connectionException.ConnectionFullPoolException;
import by.overone.online_store1.dto.UserAllInfoDTO;
import by.overone.online_store1.dto.UserDTO;
import by.overone.online_store1.dto.UserDateilsDTO;
import by.overone.online_store1.dto.UserRegistrationDTO;
import by.overone.online_store1.model.Role;
import by.overone.online_store1.model.Status;
import by.overone.online_store1.service.UserService;
import by.overone.online_store1.service.exception.ServiceException;
import by.overone.online_store1.service.exception.ServiceNotFounException;
import by.overone.online_store1.service.impl.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ServiceException, ServiceNotFounException, ConnectionFullPoolException, SQLException, ConnectionException {

        UserService userService = new UserServiceImpl();


        List<UserDTO> userDTOs = userService.getAllActiveUsers();
        userDTOs.stream().forEach(System.out::println);

//        UserDTO userDTOs = userService.getUserById(2);
//        System.out.println(userDTOs);

//        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
//        userRegistrationDTO.setLogin("Arnold");
//        userRegistrationDTO.setPassword("Arnold1111");
//        userRegistrationDTO.setEmail("arnold@gmail.com");
//        userRegistrationDTO.setRole(Role.CUSTOMER);
//        userRegistrationDTO.setStatus(Status.ACTIVE);
//        userService.addUser(userRegistrationDTO);

//        UserDateilsDTO userDateilsDTO = new UserDateilsDTO();
//        userDateilsDTO.setName("Arnold");
//        userDateilsDTO.setSurname("Schwarzenegger");
//        userDateilsDTO.setAddress("Minsk");
//        userDateilsDTO.setPhone("+375444567890");
//        userService.addUserDetails(4, userDateilsDTO);

//        userService.deleteUser(2);

//        UserAllInfoDTO userAllInfoDTO = userService.getUserAllInfo(2);
//        System.out.println(userAllInfoDTO);



    }
}
