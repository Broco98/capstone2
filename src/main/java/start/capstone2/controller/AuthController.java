package start.capstone2.controller;

import start.capstone2.service.auth.AuthService;
import start.capstone2.dto.auth.LoginRequest;
import start.capstone2.dto.auth.UserRegisterRequest;
import start.capstone2.dto.auth.UserResponse;
import start.capstone2.dto.auth.Token;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final long COOKIE_EXPIRATION = 7776000; // 90일

    @Operation(summary = "회원가입", description = "이메일(email), 비밀번호(password)와 사용자 이름(username)을 이용하여 회원가입을 합니다.", responses = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공. 사용자 정보 반환",
                    content = @Content(schema = @Schema(implementation = UserResponse.class)))
    }, tags = "인증 및 인가 기능")
    @PostMapping("/join")
    public ResponseEntity<UserResponse> signup(@RequestBody UserRegisterRequest userRegisterDto) {
        return ResponseEntity.ok(authService.join(userRegisterDto));
    }

    @Operation(summary = "로그인", description = "이메일(email)과 비밀번호(password)를 이용하여 로그인을 합니다.", responses = {
            @ApiResponse(description = "로그인 성공. 헤더의 Authorization에 access-token. 헤더의 Cookie에 refresh-token")
    }, tags = "인증 및 인가 기능")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        // User 등록 및 Refresh Token 저장
        Token tokenDto = authService.login(loginRequest);
        if (tokenDto.getAccessToken() == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Token generation failed");
        }

        // RT 저장
        HttpCookie httpCookie = ResponseCookie.from("refresh-token", tokenDto.getRefreshToken())
                .maxAge(COOKIE_EXPIRATION)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .build();
        log.info(String.valueOf(httpCookie));

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, httpCookie.toString())
                // AT 저장
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenDto.getAccessToken())
                .build();
    }

    @Operation(summary = "로그아웃", description = "헤더의 Authorization을 requestAccessToken으로 넣어 요청합니다.", responses = {
            @ApiResponse(description = "로그아웃 성공")
    }, tags = "인증 및 인가 기능")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String requestAccessToken) {
        authService.logout(requestAccessToken);
        ResponseCookie responseCookie = ResponseCookie.from("refresh-token", "")
                .maxAge(0)
                .path("/")
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .build();
    }

    @Operation(summary = "토큰 재발급", description = "쿠키의 refresh-token을 requestRefreshToken으로 넣고 "
            + "헤더의 Authorization 을 requestAccessToken으로 넣어야 합니다", responses = {
            @ApiResponse(description = "토큰 재발급 성공. 헤더의 Authorization에 access-token. 헤더의 Cookie에 refresh-token")
    }, tags = "인증 및 인가 기능")
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@CookieValue(name = "refresh-token") String requestRefreshToken,
                                     @RequestHeader("Authorization") String requestAccessToken) {
        Token reissuedTokenDto = authService.reissue(requestAccessToken, requestRefreshToken);

        if (reissuedTokenDto != null) { // 토큰 재발급 성공
            // RT 저장
            ResponseCookie responseCookie = ResponseCookie.from("refresh-token", reissuedTokenDto.getRefreshToken())
                    .maxAge(COOKIE_EXPIRATION)
                    .httpOnly(true)
                    .secure(false)
                    .build();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    // AT 저장
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + reissuedTokenDto.getAccessToken())
                    .build();

        } else { // Refresh Token 탈취 가능성
            // Cookie 삭제 후 재로그인 유도
            ResponseCookie responseCookie = ResponseCookie.from("refresh-token", "")
                    .maxAge(0)
                    .path("/")
                    .build();
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    .build();
        }
    }

}
