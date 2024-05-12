package start.capstone2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.user.User;
import start.capstone2.dto.UserRequest;
import start.capstone2.domain.user.repository.UserGroupRepository;
import start.capstone2.domain.user.repository.UserRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
//    private final GroupRepository groupRepository;
    private final UserGroupRepository groupRepository;

    @Transactional
    public Long createUser(UserRequest userRequest) {
        User user = User.createUser(userRequest.getUsername(), userRequest.getPassword(), userRequest.getName());
        userRepository.save(user);
        return user.getId();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }
}
