package start.capstone2.domain.portfolio.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.portfolio.PortfolioTechStack;

import java.util.List;

public interface PortfolioTechStackRepository extends JpaRepository<PortfolioTechStack, Long> {

    @EntityGraph(attributePaths = {"techStack", "techStack.image"}) // 한번에 조회 가능
    List<PortfolioTechStack> findAllByPortfolioId(Long portfolioId);
}
