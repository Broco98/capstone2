package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioDatabase;
import start.capstone2.domain.portfolio.PortfolioDatabaseSchema;
import start.capstone2.domain.portfolio.ShareStatus;
import start.capstone2.domain.portfolio.repository.PortfolioDatabaseRepository;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.domain.user.repository.UserRepository;
import start.capstone2.dto.portfolio.PortfolioDatabaseRequest;
import start.capstone2.dto.portfolio.PortfolioDatabaseResponse;
import start.capstone2.dto.portfolio.PortfolioDatabaseSchemaResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PortfolioDatabaseService {

    private final PortfolioRepository portfolioRepository;

    @Transactional
    public Long createPortfolioDatabase(Long userId, Long portfolioId, PortfolioDatabaseRequest request) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        if (!portfolio.getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가");
        }
        PortfolioDatabase database = PortfolioDatabase.builder()
                .portfolio(portfolio)
                .name(request.getName())
                .build();
        portfolio.addDatabase(database);
        return database.getId();
    }


    @Transactional
    public void updatePortfolioDatabase(Long userId, Long portfolioId, Long databaseId, PortfolioDatabaseRequest request) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        if (!portfolio.getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가");
        }
        PortfolioDatabase database = portfolio.getDatabases().stream().filter(d->d.getId().equals(databaseId)).findFirst().orElseThrow();
        database.updateDatabase(request.getName());
    }

    @Transactional
    public void deletePortfolioDatabase(Long userId, Long portfolioId, Long databaseId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        if (!portfolio.getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가");
        }
        PortfolioDatabase database = portfolio.getDatabases().stream().filter(d->d.getId().equals(databaseId)).findFirst().orElseThrow();
        portfolio.removeDatabase(database);
    }


    // 해당 포트폴리오의 모든 database 조회
    public List<PortfolioDatabaseResponse> findPortfolioDatabase(Long userId, Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        if (!portfolio.getUser().getId().equals(userId) && portfolio.getStatus().equals(ShareStatus.NOT_SHARED)) {
            throw new IllegalStateException("접근 불가");
        }
        List<PortfolioDatabase> databases = portfolio.getDatabases();

        List<PortfolioDatabaseResponse> results = new ArrayList<>();
        for (PortfolioDatabase database : databases) {
            List<PortfolioDatabaseSchemaResponse> schemaResponses = new ArrayList<>();
            List<PortfolioDatabaseSchema> schemas = database.getSchemas();
            for (PortfolioDatabaseSchema s : schemas) {
                schemaResponses.add(new PortfolioDatabaseSchemaResponse(s.getId(), s.getSchema(), s.getDescription()));
            }
            results.add(new PortfolioDatabaseResponse(database.getId(), database.getName(), schemaResponses));
        }
        return results;
    }

}
