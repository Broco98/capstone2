package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.portfolio.PortfolioInterview;

public interface PortfolioInterviewRepository extends JpaRepository<PortfolioInterview, Long> {
}
