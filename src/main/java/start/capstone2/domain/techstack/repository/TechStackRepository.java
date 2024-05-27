package start.capstone2.domain.techstack.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import start.capstone2.domain.techstack.TechStack;

import java.util.List;

public interface TechStackRepository extends JpaRepository<TechStack, Long> {

    @Query("select t from TechStack as t")
    @EntityGraph(attributePaths = {"image"})
    List<TechStack> findAllWithImage();

    @EntityGraph(attributePaths = {"image"})
    List<TechStack> findAllByIdIn(List<Long> ids);

}
