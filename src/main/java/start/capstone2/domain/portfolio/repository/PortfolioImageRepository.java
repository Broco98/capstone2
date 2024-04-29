package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.portfolio.PortfolioImage;

public interface PortfolioImageRepository extends JpaRepository<PortfolioImage, Long> {
}
