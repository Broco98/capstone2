package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.portfolio.PortfolioDesign;

public interface PortfolioDesignRepository extends JpaRepository<PortfolioDesign, Long> {
}
