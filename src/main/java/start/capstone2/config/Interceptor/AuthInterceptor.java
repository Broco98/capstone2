package start.capstone2.config.Interceptor;

import lombok.extern.slf4j.Slf4j;
import start.capstone2.domain.user.repository.UserRepository;
import start.capstone2.service.UserService;
import start.capstone2.service.auth.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final AuthService authService;
    private final UserRepository userRepository;

    @Autowired
    public AuthInterceptor(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = authService.getPrincipal(token);
            Long userId = userRepository.findByEmail(email).get().getId();
            log.info("interceptor userId={}", userId);
            request.setAttribute("userId", userId); // 요청 객체에 userId 저장
        }
        return true;
    }
}
