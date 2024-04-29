package start.capstone2.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.user.UserGroup;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
}
