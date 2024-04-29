package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.portfolio.PortfolioMember;

public interface PortfolioMemberRepository extends JpaRepository<PortfolioMember, Long> {
}
