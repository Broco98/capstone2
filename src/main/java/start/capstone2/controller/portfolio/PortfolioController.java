package start.capstone2.controller.portfolio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
    public Long createPortfolio(PortfolioRequest request, HttpServletRequest servletRequest) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        return portfolioService.createPortfolio(userId, request);
    }

    @Operation(summary = "update portfolio")
    @PutMapping("/{portfolioId}")
    public void updatePortfolio(@PathVariable Long portfolioId, PortfolioRequest portfolioRequest, HttpServletRequest servletRequest) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        portfolioService.updatePortfolio(userId, portfolioId, portfolioRequest);
    }

    @Operation(summary = "delete portfolio")
    @DeleteMapping("/{portfolioId}")
    public void deletePortfolio(@PathVariable Long portfolioId, HttpServletRequest servletRequest) {
        Long userId = (Long) servletRequest.getAttribute("userId");
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
