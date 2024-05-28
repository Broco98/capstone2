package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.portfolio.PortfolioFunctionModule;

import java.util.List;

public interface PortfolioFunctionModuleRepository extends JpaRepository<PortfolioFunctionModule, Long> {

    List<PortfolioFunctionModule> findAllByFunction_Id(Long portfolioFunctionId);

}
