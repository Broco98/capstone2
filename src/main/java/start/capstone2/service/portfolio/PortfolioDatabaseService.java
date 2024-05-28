package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioDatabase;
import start.capstone2.domain.portfolio.repository.PortfolioDatabaseRepository;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.domain.user.repository.UserRepository;
import start.capstone2.dto.portfolio.PortfolioDatabaseRequest;
import start.capstone2.dto.portfolio.PortfolioDatabaseResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PortfolioDatabaseService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioDatabaseRepository databaseRepository;

    // TODO user 정보 필요
    @Transactional
    public Long createPortfolioDatabase(Long userId, Long portfolioId, PortfolioDatabaseRequest request) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();

        PortfolioDatabase database = PortfolioDatabase.builder()
                .portfolio(portfolio)
                .schema(request.getSchema())
                .description(request.getDescription())
                .build();
        portfolio.addDatabase(database);
        return database.getId();
    }


    // TODO user 정보 필요
    @Transactional
    public void updatePortfolioDatabase(Long userId, Long databaseId, PortfolioDatabaseRequest request) {
        PortfolioDatabase database = databaseRepository.findById(databaseId).orElseThrow();
        database.updateDatabase(request.getSchema(), request.getDescription());
    }

    // TODO user 정보 필요
    @Transactional
    public void deletePortfolioDatabase(Long userId, Long portfolioId, Long databaseId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        PortfolioDatabase database = databaseRepository.findById(databaseId).orElseThrow();
        portfolio.removeDatabase(database);
    }

    // TODO user 정보 필요
    // 해당 포트폴리오의 모든 database 조회
    public List<PortfolioDatabaseResponse> findPortfolioDatabase(Long userId, Long portfolioId) {
        List<PortfolioDatabase> databases = databaseRepository.findAllByPortfolioId(portfolioId);
        List<PortfolioDatabaseResponse> results = new ArrayList<>();
        for (PortfolioDatabase database : databases) {
            results.add(new PortfolioDatabaseResponse(database.getId(), database.getSchema(), database.getDescription()));
        }
        return results;
    }

}
