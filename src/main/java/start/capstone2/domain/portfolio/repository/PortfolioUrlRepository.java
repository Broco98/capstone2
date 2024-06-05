//package start.capstone2.domain.portfolio.repository;
//
//import org.springframework.data.jpa.repository.EntityGraph;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import start.capstone2.domain.portfolio.PortfolioUrl;
//
//import java.util.List;
//
//public interface PortfolioUrlRepository extends JpaRepository<PortfolioUrl, Long> {
//
//    @Query("select pl from PortfolioUrl pl where pl.portfolio.id =: portfolioId")
//    @EntityGraph(attributePaths = {"url"})
//    List<PortfolioUrl> findAllByPortfolioIdWithUrl(Long portfolioId);
//
//}
