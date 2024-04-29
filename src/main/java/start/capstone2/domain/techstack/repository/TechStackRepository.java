package start.capstone2.domain.techstack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.techstack.TechStack;

public interface TechStackRepository extends JpaRepository<TechStack, Long> {
}
