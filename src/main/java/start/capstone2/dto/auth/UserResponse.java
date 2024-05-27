package start.capstone2.dto.auth;

import start.capstone2.domain.user.User;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    private String email;
    private String username;
    private Long userId;
    private LocalDateTime createdDate;

    public static UserResponse of(User user) {
        return UserResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .createdDate(user.getCreatedDate())
                .build();
    }
}
