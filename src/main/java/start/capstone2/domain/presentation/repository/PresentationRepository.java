package start.capstone2.domain.presentation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import start.capstone2.domain.presentation.Presentation;

import java.util.List;

@Repository
public interface PresentationRepository extends JpaRepository<Presentation, Long> {
    List<Presentation> findByPresentationType(String presentationType);
}
