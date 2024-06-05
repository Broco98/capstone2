package start.capstone2.config;

import start.capstone2.domain.user.repository.UserRepository;
import start.capstone2.jwt.JwtAuthenticationFilter;
import start.capstone2.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import start.capstone2.service.auth.AuthService;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JwtProvider jwtProvider;
    private final AuthService authService;
    private final UserRepository userRepository;

    // JwtProvider 를 주입받아서 JwtFilter 를 통해 Security 로직에 필터를 등록
    @Override
    public void configure(HttpSecurity http) {
        JwtAuthenticationFilter customFilter = new JwtAuthenticationFilter(jwtProvider, authService, userRepository);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
