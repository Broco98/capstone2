package start.capstone2.controller.portfolio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioRequest;
import start.capstone2.dto.portfolio.PortfolioResponse;
import start.capstone2.service.portfolio.PortfolioService;

import java.util.List;

@Tag(name = "Portfolio api", description = "portfolio 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    @Operation(summary = "create portfolio", description = "portfolio 하나 생성, 연관관계가 복잡하게 연결돼있어, 비어있는 portfolio라도 먼저 생성할 것")
    @PostMapping("")
    public Long createPortfolio(Long userId, PortfolioRequest request) {
        return portfolioService.createPortfolio(userId, request);
    }
    
    // TODO -> user 쪽으로 가야함
    @Operation(summary = "find All user's portfolio", description = "해당 유저의 모든 포트폴리오 조회")
    @GetMapping("")
    public ResponseResult<List<PortfolioResponse>> findAllPortfolio(Long userId) {
        List<PortfolioResponse> results = portfolioService.findAllByUserId(userId);
        return new ResponseResult<>(results);
    }
    
    @Operation(summary = "update portfolio", description = "portfolio의 기본 정보 업데이트")
    @PutMapping("/{portfolioId}")
    public void updatePortfolio(Long userId, PortfolioRequest portfolioRequest, @PathVariable Long portfolioId) {
        portfolioService.updatePortfolio(userId, portfolioId, portfolioRequest);
    }

    @Operation(summary = "delete portfolio", description = "선택한 portfolio 단일 삭제")
    @DeleteMapping("/{portfolioId}")
    public void deletePortfolio(Long userId, @PathVariable Long portfolioId) {
        portfolioService.deletePortfolio(userId, portfolioId);
    }

    @Operation(summary = "find portfolio", description = "portfolio 단일 조회")
    @GetMapping("/{portfolioId}")
    public PortfolioResponse findPortfolio(@PathVariable Long portfolioId) {
        return portfolioService.findById(portfolioId);
    }

}
