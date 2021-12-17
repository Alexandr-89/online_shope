package by.overone.online_store1.dto;

import by.overone.online_store1.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductDTO {
    private Long id;
    private String name;
    private String description;
    private long price;
    private long count;
    private Status status;

}
