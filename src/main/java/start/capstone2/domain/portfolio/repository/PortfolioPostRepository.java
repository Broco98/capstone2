package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.portfolio.PortfolioPost;

public interface PortfolioPostRepository extends JpaRepository<PortfolioPost, Long> {
}
