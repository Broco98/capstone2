package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import start.capstone2.domain.portfolio.PortfolioApi;
import start.capstone2.domain.portfolio.PortfolioFunction;

import java.util.List;

public interface PortfolioFunctionRepository extends JpaRepository<PortfolioFunction, Long> {

    @Query("select pf from PortfolioFunction pf where pf.id =: id")
    @EntityGraph(attributePaths = {"Image"})
    PortfolioFunction findByIdWithImage(Long id);

    @EntityGraph(attributePaths = {"Image"})
    List<PortfolioFunction> findAllByPortfolioId(Long portfolioId);
}
