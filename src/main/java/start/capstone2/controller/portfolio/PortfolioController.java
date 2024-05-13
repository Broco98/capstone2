package start.capstone2.controller.portfolio;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import start.capstone2.ResponseResult;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioGroup;
import start.capstone2.domain.portfolio.PortfolioImage;
import start.capstone2.dto.portfolio.PortfolioRequest;
import start.capstone2.dto.portfolio.PortfolioResponse;
import start.capstone2.service.portfolio.PortfolioDetailService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PortfolioDetailService portfolioService;

    @PostMapping("/")
    public ResponseEntity<Long> createPortfolio(Long userId) {
        Long portfolioId = portfolioService.createPortfolio(userId);

        return ResponseEntity.ok(portfolioId);
    }

    @GetMapping("/{portfolioId}")
    public ResponseResult<PortfolioResponse> getSinglePortfolio(Long userId, @PathVariable Long portfolioId) {
        Portfolio portfolio = portfolioService.findById(portfolioId);

        return new ResponseResult<>(portfolio);
    }

    @PutMapping("/{portfolioId}")
    public void updatePortfolio(Long userId, PortfolioRequest portfolioRequest, @PathVariable Long portfolioId) {


    }

}
