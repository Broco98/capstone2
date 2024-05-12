package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.portfolio.PortfolioSchedule;

public interface PortfolioScheduleRepository extends JpaRepository<PortfolioSchedule, Long> {
}
