package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.portfolio.PortfolioComment;

public interface PortfolioCommentRepository extends JpaRepository<PortfolioComment, Long> {
}
