package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.portfolio.PortfolioDatabase;

public interface PortfolioDatabaseRepository extends JpaRepository<PortfolioDatabase, Long> {
}
