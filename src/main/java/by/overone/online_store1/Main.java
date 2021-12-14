package by.overone.online_store1;

import by.overone.online_store1.dto.UserDTO;
import by.overone.online_store1.dto.UserRegistrationDTO;
import by.overone.online_store1.service.UserService;
import by.overone.online_store1.service.exception.ServiceException;
import by.overone.online_store1.service.exception.ServiceNotFounException;
import by.overone.online_store1.service.impl.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) throws ServiceException, ServiceNotFounException {

        UserService userService = new UserServiceImpl();


//        List<UserDTO> userDTOs = userService.getAllActiveUsers();
//        userDTOs.stream().forEach(System.out::println);

//        UserDTO userDTOs = userService.getUserById(3);
//        System.out.println(userDTOs);

        UserRegistrationDTO user = new UserRegistrationDTO();
        user.setLogin("irina");
        user.setPassword("irina1111");
        user.setEmail("irina@gmail.com");
        userService.addUser(user);

    }
}
