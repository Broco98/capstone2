package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.portfolio.PortfolioFunction;

public interface PortfolioFunctionRepository extends JpaRepository<PortfolioFunction, Long> {
}
