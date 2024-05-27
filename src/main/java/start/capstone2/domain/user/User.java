package start.capstone2.domain.user;

import jakarta.persistence.*;
import lombok.*;
import start.capstone2.domain.BaseEntity;

@Table(name = "users")
@Entity
@Getter
//@Builder
//@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;
    private String email;
    private String password;
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;


    @Builder
    private User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = Role.USER;
    }
    public static User of(String username, String email) {
        return User.builder()
                .username(username)
                .email(email)
                .build();
    }

    public String getRoleValue() {
        return this.getRole().getValue();
    }


    public static User createUser(String username, String password, String name) {
        User user = new User();
        user.username = username;
        user.password = password;
        user.name = name;

        return user;
    }

}
