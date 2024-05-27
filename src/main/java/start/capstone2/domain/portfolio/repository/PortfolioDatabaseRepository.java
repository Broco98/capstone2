package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.portfolio.PortfolioDatabase;

import java.util.List;

public interface PortfolioDatabaseRepository extends JpaRepository<PortfolioDatabase, Long> {

    List<PortfolioDatabase> findAllByPortfolioId(Long portfolioId);

}
