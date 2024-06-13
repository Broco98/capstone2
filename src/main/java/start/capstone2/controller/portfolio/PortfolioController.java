package start.capstone2.controller.portfolio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioRequest;
import start.capstone2.dto.portfolio.PortfolioResponse;
import start.capstone2.service.portfolio.PortfolioService;

import java.util.List;

@Slf4j
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
        log.info("portfolio request={}", request);
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
    public PortfolioResponse findPortfolio(@PathVariable Long portfolioId, HttpServletRequest servletRequest) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        return portfolioService.findById(userId, portfolioId);
    }

    @Operation(summary = "find all portfolio", description = "공유된 모든 포트폴리오를 조회합니다.")
    @GetMapping("")
    public ResponseResult<List<PortfolioResponse>> findAllSharedPortfolio() {
        List<PortfolioResponse> results = portfolioService.findAllBySharedStatus();
        return new ResponseResult<>(results);
    }
}
