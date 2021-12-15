package by.overone.online_store1.dto;

import by.overone.online_store1.model.Role;
import by.overone.online_store1.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAllInfoDTO {

//    private long id;
    private String login;
    private String password;
    private String email;
    private Role role;
    private Status status;
    private String name;
    private String surname;
    private String address;
    private String phone;
}
