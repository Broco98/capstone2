package start.capstone2.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.user.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
