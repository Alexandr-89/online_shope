package by.overone.online_store1.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class User {

    private long id;
    private String login;
    private String password;
    private String email;
    private Role role;
    private Status status;
}
