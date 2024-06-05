package start.capstone2.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}
