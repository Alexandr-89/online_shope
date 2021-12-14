package by.overone.online_store1;

import by.overone.online_store1.dto.UserDTO;
import by.overone.online_store1.dto.UserDateilsDTO;
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

//        UserDTO userDTOs = userService.getUserById(5);
//        System.out.println(userDTOs);

        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setLogin("irina");
        userRegistrationDTO.setPassword("irina1111");
        userRegistrationDTO.setEmail("irina@gmail.com");
        userService.addUser(userRegistrationDTO);

//        UserDateilsDTO userDateilsDTO = new UserDateilsDTO();
//        userDateilsDTO.setName("Irina");
//        userDateilsDTO.setSurname("Abramovich");
//        userDateilsDTO.setAddress("Minsk");
//        userDateilsDTO.setPhone("+375334567890");
//        userRegistrationDTO.setId(6);


    }
}
