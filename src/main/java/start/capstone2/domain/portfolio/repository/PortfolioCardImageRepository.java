package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.portfolio.PortfolioCardImage;

public interface PortfolioCardImageRepository extends JpaRepository<PortfolioCardImage, Long> {
}
