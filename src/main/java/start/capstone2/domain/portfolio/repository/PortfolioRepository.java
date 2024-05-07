package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.user.User;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    List<Portfolio> findAllByUserId(Long userId); // JoinColum 설정해 놨으므로 id로 조회 가능

    @Query("""
            select p
            from Portfolio as p
            join fetch PortfolioImage as pl
            join fetch PortfolioCardImage as pc
            join fetch PortfolioGroup as pg
            join fetch Group as g
            where p.user.id = :userId
    """)
    List<Portfolio> findAllByUserIdWithImages(Long userId);

    // TODO
    @Query("""
            select p
            from Portfolio as p
            join fetch p.cardImage
            join fetch p.images
            join fetch p.sharedGroups
            where p.user.id = :userId and p.id = :portfolioId
    """)
    Portfolio findByUserIdAndPortfolioIdWithImages(Long userId, Long portfolioId);
}
