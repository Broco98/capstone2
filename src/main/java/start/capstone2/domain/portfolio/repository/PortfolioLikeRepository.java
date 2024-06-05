package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.portfolio.PortfolioLike;

import java.util.List;

public interface PortfolioLikeRepository extends JpaRepository<PortfolioLike, Long> {

    @EntityGraph(attributePaths = {"portfolio"})
    List<PortfolioLike> findAllByUserId(Long userId);
}
