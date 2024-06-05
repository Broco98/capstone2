package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.portfolio.PortfolioApiModule;

public interface PortfolioApiModuleRepository extends JpaRepository<PortfolioApiModule, Long> {

}
