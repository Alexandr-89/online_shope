package by.overone.online_store1.dto;

import by.overone.online_store1.model.Role;
import by.overone.online_store1.model.Status;
import by.overone.online_store1.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
@RequiredArgsConstructor
public class UserDTO {

    private long id;
    private String login;
    private String email;
    private Role role;
    private Status status;

//    public UserDTO(long id, String login, String email, Role role, Status status) {
//        this.id = id;
//        this.login = login;
//        this.email = email;
//        this.role = role;
//        this.status = status;
//    }
}
