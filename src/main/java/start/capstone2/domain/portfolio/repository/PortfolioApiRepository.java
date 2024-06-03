package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.portfolio.PortfolioApi;

import java.util.List;

public interface PortfolioApiRepository extends JpaRepository<PortfolioApi, Long> {

    List<PortfolioApi> findAllByPortfolioId(Long portfolioId);

    @EntityGraph(attributePaths = {"module"})
    PortfolioApi findByIdWithModule(Long id);

}
