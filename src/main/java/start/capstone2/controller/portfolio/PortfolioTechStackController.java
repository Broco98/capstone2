package start.capstone2.controller.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioTechStack;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioTechStackRequest;
import start.capstone2.dto.portfolio.PortfolioTechStackResponse;
import start.capstone2.service.portfolio.PortfolioTechStackService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioTechStackController {

    private final PortfolioTechStackService techStackService;

    @PostMapping("/{portfolioId}/tech-stack")
    public Long createPortfolioTechStack(Long userId, @PathVariable Long portfolioId, PortfolioTechStackRequest techStackRequest) {
        return techStackService.createPortfolioTechStack(userId, portfolioId, techStackRequest);
    }

    @GetMapping("/{portfolioId}/tech-stack")
    public ResponseResult<List<PortfolioTechStackResponse>> findAllPortfolioTechStack(Long userId, @PathVariable Long portfolioId) {
        return new ResponseResult<>(techStackService.findAllPortfolioTechStack(userId, portfolioId));
    }

    @DeleteMapping("/{portfolioId}/tech-stack/{techStackId}")
    public void deletePortfolioTechStack(Long userId, @PathVariable Long portfolioId, @PathVariable Long techStackId) {
        techStackService.deletePortfolioTechStack(userId, portfolioId, techStackId);
    }
}
