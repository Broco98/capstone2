package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import start.capstone2.domain.portfolio.Portfolio;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    @EntityGraph(attributePaths = {"cardImage"})
    List<Portfolio> findAllByUserId(Long userId); // JoinColum 설정해 놨으므로 id로 조회 가능
    
    @Query("select p from Portfolio p where p.status =: SHARE")
    @EntityGraph(attributePaths = {"cardImage"})
    List<Portfolio> findAllBySharedStatus();
}
