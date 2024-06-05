package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import start.capstone2.domain.portfolio.PortfolioDesign;

import java.util.List;

public interface PortfolioDesignRepository extends JpaRepository<PortfolioDesign, Long> {

    List<PortfolioDesign> findAllByPortfolioId(Long portfolioId);
}
