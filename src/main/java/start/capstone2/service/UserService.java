package start.capstone2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.user.User;
import start.capstone2.dto.user.UserRequest;
import start.capstone2.domain.user.repository.UserRepository;

// TODO 통합 예정
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long createUser(UserRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(request.getPassword())
                .build();
        userRepository.save(user);
        return user.getId();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }
}
