package start.capstone2.controller.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioRequest;
import start.capstone2.dto.portfolio.PortfolioResponse;
import start.capstone2.service.portfolio.PortfolioService;

import java.util.List;

@Tag(name = "Portfolio Api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    @Operation(summary = "create portfolio")
    @PostMapping("")
    public Long createPortfolio(Long userId, PortfolioRequest request) {
        return portfolioService.createPortfolio(userId, request);
    }

    @Operation(summary = "update portfolio")
    @PutMapping("/{portfolioId}")
    public void updatePortfolio(Long userId, @PathVariable Long portfolioId, PortfolioRequest portfolioRequest) {
        portfolioService.updatePortfolio(userId, portfolioId, portfolioRequest);
    }

    @Operation(summary = "delete portfolio")
    @DeleteMapping("/{portfolioId}")
    public void deletePortfolio(Long userId, @PathVariable Long portfolioId) {
        portfolioService.deletePortfolio(userId, portfolioId);
    }

    @Operation(summary = "find portfolio")
    @GetMapping("/{portfolioId}")
    public PortfolioResponse findPortfolio(@PathVariable Long portfolioId) {
        return portfolioService.findById(portfolioId);
    }

    // TODO -> user 쪽으로 가야함
    @Operation(summary = "find all portfolio")
    @GetMapping("")
    public ResponseResult<List<PortfolioResponse>> findAllPortfolio(Long userId) {
        List<PortfolioResponse> results = portfolioService.findAllByUserId(userId);
        return new ResponseResult<>(results);
    }
}
