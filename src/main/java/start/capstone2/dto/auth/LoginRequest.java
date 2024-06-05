package start.capstone2.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
public class LoginRequest {
    @NotNull(message = "ID cannot be null")
    private String email;
    @NotNull(message = "password cannot be null")
    private String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }

}
