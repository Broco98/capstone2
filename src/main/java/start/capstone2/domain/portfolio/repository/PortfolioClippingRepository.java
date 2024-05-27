package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.portfolio.PortfolioClipping;

import java.util.List;

public interface PortfolioClippingRepository extends JpaRepository<PortfolioClipping, Long> {

    @EntityGraph(attributePaths = {"portfolio"})
    List<PortfolioClipping> findAllByUserId(Long userId);
}
