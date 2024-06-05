package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import start.capstone2.domain.portfolio.PortfolioApi;
import start.capstone2.domain.portfolio.PortfolioFunction;

import java.util.List;

public interface PortfolioFunctionRepository extends JpaRepository<PortfolioFunction, Long> {

    List<PortfolioFunction> findAllByPortfolioId(Long portfolioId);

    @Query("select distinct f from PortfolioFunction f where f.portfolio.id =: portfolioId")
    @EntityGraph(attributePaths = {"modules"})
    List<PortfolioFunction> findAllByPortfolioIdWithModule(Long portfolioId);

    @Query("select distinct f from PortfolioFunction f where f.id =:functionId")
    @EntityGraph(attributePaths = {"modules"})
    PortfolioFunction findByIdWithModules(Long functionId);
}
