package start.capstone2.config;

import start.capstone2.domain.user.repository.UserRepository;
import start.capstone2.jwt.JwtProvider;
import start.capstone2.jwt.JwtAuthenticationFilter;
import start.capstone2.jwt.JwtAuthenticationEntryPoint;
import start.capstone2.jwt.JwtAccessDeniedHandler;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;
import start.capstone2.service.auth.AuthService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final CorsConfig corsConfig;
    private final AuthService authService;
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
                .cors(Customizer.withDefaults()) // CORS 기본 설정 사용
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 정책을 STATELESS로 설정
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint) // 인증 실패 처리
                        .accessDeniedHandler(jwtAccessDeniedHandler)) // 접근 거부 처리
                .authorizeHttpRequests(auth -> auth
                        //.requestMatchers("/auth/join", "/auth/login", "/auth/logout", "/auth/reissue").permitAll() // 인증 없이 접근 허용
                        .requestMatchers("/portfolio/**").authenticated()
                        .anyRequest().permitAll()) // 나머지 요청은 인증 필요
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider, authService, userRepository), UsernamePasswordAuthenticationFilter.class) // JWT 필터 추가
                .addFilter(corsConfig.corsFilter()); // 사용자 정의 CORS 필터 추가

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
