package start.capstone2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.user.User;
import start.capstone2.dto.user.UserRequest;
import start.capstone2.domain.user.repository.UserRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;



    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }
}
