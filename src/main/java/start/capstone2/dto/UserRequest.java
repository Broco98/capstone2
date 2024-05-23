package start.capstone2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

// TODO
@Data
@AllArgsConstructor
public class UserRequest {

    private String username;
    private String password;
    private String name;

}
