package start.capstone2.domain.presentation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.presentation.PresentationFile;

import java.util.List;

public interface PresentationFileRepository extends JpaRepository<PresentationFile, Long> {
    List<PresentationFile> findByUserId(Long userId);
    List<PresentationFile> findByPortfolioId(Long portfolioId);
}
