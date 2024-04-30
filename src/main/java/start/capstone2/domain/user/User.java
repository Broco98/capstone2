package start.capstone2.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String username;
    private String password;
    private String name;

    @OneToMany(mappedBy = "user")
    private List<UserGroup> groups = new ArrayList<>();

    public static User createUser(String username, String password, String name) {
        User user = new User();
        user.username = username;
        user.password = password;
        user.name = name;

        return user;
    }

}
