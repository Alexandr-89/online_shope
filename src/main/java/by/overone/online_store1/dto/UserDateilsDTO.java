package by.overone.online_store1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDateilsDTO {
    private String name;
    private String surname;
    private String address;
    private String phone;
}
