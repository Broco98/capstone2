package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioDatabase;
import start.capstone2.domain.portfolio.PortfolioDatabaseSchema;
import start.capstone2.domain.portfolio.repository.PortfolioDatabaseRepository;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.domain.user.repository.UserRepository;
import start.capstone2.dto.portfolio.PortfolioDatabaseRequest;
import start.capstone2.dto.portfolio.PortfolioDatabaseResponse;
import start.capstone2.dto.portfolio.PortfolioDatabaseSchemaRequest;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PortfolioDatabaseSchemaService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioDatabaseRepository databaseRepository;

    @Transactional
    public Long createPortfolioDatabaseSchema(Long userId, Long databaseId, PortfolioDatabaseSchemaRequest request) {
        PortfolioDatabase database = databaseRepository.findById(databaseId).orElseThrow();

        if (!database.getPortfolio().getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가");
        }

        PortfolioDatabaseSchema schema = PortfolioDatabaseSchema.builder()
                .database(database)
                .schema(request.getSchema())
                .description(request.getDescription())
                .build();
        database.addSchema(schema);
        return schema.getId();
    }

    @Transactional
    public void updatePortfolioDatabaseSchema(Long userId, Long databaseId, Long schemaId, PortfolioDatabaseSchemaRequest request) {
        PortfolioDatabase database = databaseRepository.findById(databaseId).orElseThrow();
        if (!database.getPortfolio().getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가");
        }
        PortfolioDatabaseSchema schema = database.getSchemas().stream()
                .filter(d->d.getId().equals(schemaId))
                .findFirst().orElseThrow();
        schema.updateDatabaseSchema(request.getSchema(), request.getDescription());
    }

    @Transactional
    public void deletePortfolioDatabaseSchema(Long userId, Long databaseId, Long schemaId) {
        PortfolioDatabase database = databaseRepository.findById(databaseId).orElseThrow();
        if (!database.getPortfolio().getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가");
        }
        PortfolioDatabaseSchema schema = database.getSchemas().stream()
                .filter(d->d.getId().equals(schemaId))
                .findFirst().orElseThrow();
        database.removeSchema(schema);
    }

    // TODO user 정보 필요
//    // database의 모든 schema 조회
//    public List<PortfolioDatabaseResponse> findPortfolioDatabase(Long userId, Long portfolioId) {
//        List<PortfolioDatabase> databases = databaseRepository.findAllByPortfolioId(portfolioId);
//        List<PortfolioDatabaseResponse> results = new ArrayList<>();
//        for (PortfolioDatabase database : databases) {
//            results.add(new PortfolioDatabaseResponse(database.getId(), database.getSchema(), database.getDescription()));
//        }
//        return results;
//    }

}
