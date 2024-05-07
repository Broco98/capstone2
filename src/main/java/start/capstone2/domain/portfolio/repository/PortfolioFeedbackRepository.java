package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.portfolio.PortfolioFeedback;

import java.util.List;

public interface PortfolioFeedbackRepository extends JpaRepository <PortfolioFeedback, Long>{

    List<PortfolioFeedback> findAllByPortfolioId(Long portfolioId);
}
