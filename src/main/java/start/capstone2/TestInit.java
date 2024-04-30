package start.capstone2;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import start.capstone2.domain.user.dto.UserRequest;
import start.capstone2.service.GroupService;
import start.capstone2.service.UserService;

@Component
@RequiredArgsConstructor
public class TestInit {

    private final UserService userService;
    private final GroupService groupService;

    @PostConstruct
    public void init() {
        userService.createUser(new UserRequest("id1", "pw1", "test1"));
        userService.createUser(new UserRequest("id2", "pw2", "test2"));
        userService.createUser(new UserRequest("id3", "pw3", "test3"));

        groupService.createGroup("group1");
        groupService.createGroup("all");
    }



}
