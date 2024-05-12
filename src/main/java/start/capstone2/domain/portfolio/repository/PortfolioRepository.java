package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.user.User;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    List<Portfolio> findAllByUserId(Long userId); // JoinColum 설정해 놨으므로 id로 조회 가능
    
}
