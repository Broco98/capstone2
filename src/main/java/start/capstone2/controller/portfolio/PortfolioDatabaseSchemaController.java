package start.capstone2.controller.portfolio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioDatabaseRequest;
import start.capstone2.dto.portfolio.PortfolioDatabaseResponse;
import start.capstone2.dto.portfolio.PortfolioDatabaseSchemaRequest;
import start.capstone2.service.portfolio.PortfolioDatabaseSchemaService;
import start.capstone2.service.portfolio.PortfolioDatabaseService;

import java.util.List;

@Tag(name = "Portfolio Database Schema Api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio-database")
public class PortfolioDatabaseSchemaController {

    private final PortfolioDatabaseSchemaService schemaService;

    @Operation(summary = "create portfolio database schema")
    @PostMapping("/{databaseId}/schema")
    public Long createPortfolioDatabase(Long userId, @PathVariable Long databaseId, PortfolioDatabaseSchemaRequest request) {
        return schemaService.createPortfolioDatabaseSchema(userId, databaseId, request);
    }

    @Operation(summary = "update portfolio database schema")
    @PutMapping("/{databaseId}/schema/{schemaId}")
    public void updatePortfolioDesign(Long userId, @PathVariable Long databaseId, @PathVariable Long schemaId, PortfolioDatabaseSchemaRequest request) {
        schemaService.updatePortfolioDatabaseSchema(userId, databaseId, schemaId, request);
    }

    @Operation(summary = "delete portfolio database schema")
    @DeleteMapping("/{databaseId}/schema/{schemaId}")
    public void deletePortfolioPortfolioDesign(Long userId, @PathVariable Long databaseId, @PathVariable Long schemaId) {
        schemaService.deletePortfolioDatabaseSchema(userId, databaseId, schemaId);
    }

//    @Operation(summary = "find all portfolio database", description = "포트폴리오의 모든 database 조회")
//    @GetMapping("/{portfolioId}/database")
//    public ResponseResult<List<PortfolioDatabaseResponse>> findAllPortfolioDatabase(Long userId, @PathVariable Long portfolioId) {
//        List<PortfolioDatabaseResponse> result = databaseService.findPortfolioDatabase(userId, portfolioId);
//        return new ResponseResult<>(result);
//    }

}
