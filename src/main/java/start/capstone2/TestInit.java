package start.capstone2;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import start.capstone2.dto.UserRequest;
import start.capstone2.service.UserService;

@Component
@RequiredArgsConstructor
public class TestInit {

    private final UserService userService;

    @PostConstruct
    public void init() {
        userService.createUser(new UserRequest("test1", "1111", "test1"));
        userService.createUser(new UserRequest("test2", "2222", "test2"));
        userService.createUser(new UserRequest("test3", "3333", "test3"));
    }



}
