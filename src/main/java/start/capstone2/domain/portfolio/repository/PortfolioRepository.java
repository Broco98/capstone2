package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.portfolio.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}
