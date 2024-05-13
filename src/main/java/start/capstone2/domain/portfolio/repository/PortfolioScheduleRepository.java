package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.portfolio.PortfolioApi;
import start.capstone2.domain.portfolio.PortfolioSchedule;

import java.util.List;

public interface PortfolioScheduleRepository extends JpaRepository<PortfolioSchedule, Long> {
    List<PortfolioSchedule> findAllByPortfolioId(Long portfolioId);
}
