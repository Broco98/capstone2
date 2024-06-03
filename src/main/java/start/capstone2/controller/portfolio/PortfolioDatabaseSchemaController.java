package start.capstone2.controller.portfolio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioDatabaseRequest;
import start.capstone2.dto.portfolio.PortfolioDatabaseResponse;
import start.capstone2.service.portfolio.PortfolioDatabaseService;

import java.util.List;

@Tag(name = "PortfolioDatabase api", description = "portfolio database 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioDatabaseSchemaController {

    private final PortfolioDatabaseService databaseService;

    @Operation(summary = "create portfolio database", description = "포트폴리오 database 단일 생성")
    @PostMapping("/{portfolioId}/database")
    public Long createPortfolioDatabase(Long userId, @PathVariable Long portfolioId, PortfolioDatabaseRequest request) {
        return databaseService.createPortfolioDatabase(userId, portfolioId, request);
    }

    @Operation(summary = "update portfolio database", description = "포트폴리오 database 수정")
    @PutMapping("/{portfolioId}/design/{databaseId}")
    public void updatePortfolioDesign(Long userId, @PathVariable Long portfolioId, @PathVariable Long databaseId, PortfolioDatabaseRequest request) {
        databaseService.updatePortfolioDatabase(userId, databaseId, request);
    }

    @Operation(summary = "delete portfolio database", description = "포트폴리오 database 단일 삭제")
    @DeleteMapping("/{portfolioId}/database/{databaseId}")
    public void deletePortfolioPortfolioDesign(Long userId, @PathVariable Long portfolioId, @PathVariable Long databaseId) {
        databaseService.deletePortfolioDatabase(userId, portfolioId, databaseId);
    }

    @Operation(summary = "find all portfolio database", description = "포트폴리오의 모든 database 조회")
    @GetMapping("/{portfolioId}/database")
    public ResponseResult<List<PortfolioDatabaseResponse>> findAllPortfolioDatabase(Long userId, @PathVariable Long portfolioId) {
        List<PortfolioDatabaseResponse> result = databaseService.findPortfolioDatabase(userId, portfolioId);
        return new ResponseResult<>(result);
    }


}
