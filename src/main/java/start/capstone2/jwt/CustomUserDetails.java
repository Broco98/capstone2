package start.capstone2.jwt;

import start.capstone2.domain.user.User;
import java.util.ArrayList;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> user.getRole().getKey()); // key: ROLE_권한
        return authorities;
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() { // 계정의 만료 여부
        return true;
    }     // 계정의 만료 여부

    @Override
    public boolean isAccountNonLocked() { // 계정의 잠김 여부
        return true;
    }      // 계정의 잠김 여부

    @Override
    public boolean isCredentialsNonExpired() { // 비밀번호 만료 여부
        return true;
    } // 비밀번호 만료 여부

    @Override
    public boolean isEnabled() { // 계정의 활성화 여부
        return true;
    }               // 계정의 활성화 여부
}

