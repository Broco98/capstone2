package start.capstone2.domain.techstack.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.techstack.TechStack;

import java.util.List;

public interface TechStackRepository extends JpaRepository<TechStack, Long> {

    @EntityGraph(attributePaths = {"image"})
    List<TechStack> findAllWithImage();
}
