package start.capstone2.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
