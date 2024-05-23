package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.portfolio.PortfolioLike;

public interface PortfolioLikeRepository extends JpaRepository<PortfolioLike, Long> {
}
