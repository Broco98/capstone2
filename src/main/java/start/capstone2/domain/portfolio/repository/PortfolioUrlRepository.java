package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.portfolio.PortfolioUrl;

public interface PortfolioUrlRepository extends JpaRepository<PortfolioUrl, Long> {
}
