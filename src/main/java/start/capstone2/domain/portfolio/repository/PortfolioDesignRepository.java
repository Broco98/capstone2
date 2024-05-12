package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioDesign;

public interface PortfolioDesignRepository extends JpaRepository<PortfolioDesign, Long> {

    @Query("select pd from PortfolioDesign pd where pd.id =: id")
    @EntityGraph(attributePaths = {"image"})
    PortfolioDesign findByIdWithImage(Long id);
}
