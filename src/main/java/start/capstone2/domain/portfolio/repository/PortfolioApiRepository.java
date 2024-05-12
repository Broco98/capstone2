package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.portfolio.PortfolioApi;

public interface PortfolioApiRepository extends JpaRepository<PortfolioApi, Long> {
}
