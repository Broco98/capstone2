package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.portfolio.PortfolioDetail;

public interface PortfolioDetailRepository extends JpaRepository<PortfolioDetail, Long> {
}
