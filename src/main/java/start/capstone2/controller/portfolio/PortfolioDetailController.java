package start.capstone2.controller.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.portfolio.PortfolioDesignRequest;
import start.capstone2.dto.portfolio.PortfolioDetailRequest;
import start.capstone2.service.portfolio.PortfolioDesignService;
import start.capstone2.service.portfolio.PortfolioDetailService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioDetailController {

    private final PortfolioDetailService detailService;

    @PostMapping("/{portfolioId}/detail")
    public Long createPortfolioDetail(Long userId, @PathVariable Long portfolioId, PortfolioDetailRequest request) {
        return detailService.createPortfolioDetail(userId, portfolioId, request);
    }

    @PutMapping("/{portfolioId}/detail")
    public void updatePortfolioDetail(Long userId, @PathVariable Long portfolioId, PortfolioDetailRequest request) {
        detailService.updatePortfolioDetail(userId, portfolioId, request);
    }

    @GetMapping("/{portfolioId}/detail")
    public void findPortfolioDetail(Long userId, @PathVariable Long portfolioId) {
        detailService.findPortfolioDetail(userId, portfolioId);
    }
}
