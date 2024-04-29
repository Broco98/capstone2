package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.portfolio.PortfolioFeedback;

public interface PortfolioFeedbackRepository extends JpaRepository <PortfolioFeedback, Long>{
}
