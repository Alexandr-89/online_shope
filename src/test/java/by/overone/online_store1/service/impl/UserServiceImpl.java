package by.overone.online_store1.service.impl;

import by.overone.online_store1.dao.UserDAO;
import by.overone.online_store1.dto.UserDTO;
import by.overone.online_store1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;


    @Override
    public List<UserDTO> getAllUsers() {
        List<UserDTO> userDTOs = userDAO.getAllUsers().stream()
                .map(user -> new UserDTO(user.getId(), user.getLogin(), user.getEmail(), user.getRole(), user.getStatus()))
                .collect(Collectors.toList());
        return null;
    }
}
