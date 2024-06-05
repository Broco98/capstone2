package start.capstone2.controller.portfolio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioDatabaseRequest;
import start.capstone2.dto.portfolio.PortfolioDatabaseResponse;
import start.capstone2.service.portfolio.PortfolioDatabaseService;

import java.util.List;

@Tag(name = "Portfolio Database Api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioDatabaseController {

    private final PortfolioDatabaseService databaseService;

    @Operation(summary = "create portfolio database")
    @PostMapping("/{portfolioId}/database")
    public Long createPortfolioDatabase(HttpServletRequest servletRequest, @PathVariable Long portfolioId, PortfolioDatabaseRequest request) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        return databaseService.createPortfolioDatabase(userId, portfolioId, request);
    }

    @Operation(summary = "update portfolio database")
    @PutMapping("/{portfolioId}/design/{databaseId}")
    public void updatePortfolioDesign(HttpServletRequest servletRequest, @PathVariable Long portfolioId, @PathVariable Long databaseId, PortfolioDatabaseRequest request) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        databaseService.updatePortfolioDatabase(userId, portfolioId, databaseId, request);
    }

    @Operation(summary = "delete portfolio database")
    @DeleteMapping("/{portfolioId}/database/{databaseId}")
    public void deletePortfolioPortfolioDesign(HttpServletRequest servletRequest, @PathVariable Long portfolioId, @PathVariable Long databaseId) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        databaseService.deletePortfolioDatabase(userId, portfolioId, databaseId);
    }

    @Operation(summary = "find all portfolio database")
    @GetMapping("/{portfolioId}/database")
    public ResponseResult<List<PortfolioDatabaseResponse>> findAllPortfolioDatabase(HttpServletRequest servletRequest, @PathVariable Long portfolioId) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        List<PortfolioDatabaseResponse> result = databaseService.findPortfolioDatabase(userId, portfolioId);
        return new ResponseResult<>(result);
    }


}
