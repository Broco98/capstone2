package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.portfolio.PortfolioCode;

import java.util.List;

public interface PortfolioCodeRepository extends JpaRepository<PortfolioCode, Long> {
    List<PortfolioCode> findAllByPortfolioId(Long portfolioId);
}
