package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.portfolio.PortfolioClipping;

public interface PortfolioClippingRepository extends JpaRepository<PortfolioClipping, Long> {
}
