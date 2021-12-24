package by.overone.online_store1.controller;

import by.overone.online_store1.dto.UserDTO;
import by.overone.online_store1.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDTO> readAll(){
        return userService.getAllUsers();
    }

    @GetMapping("/hello")
    public String read(){
        return "hello";
    }
}
