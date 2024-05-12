package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.portfolio.PortfolioCode;

public interface PortfolioCodeRepository extends JpaRepository<PortfolioCode, Long> {
}
