package start.capstone2.controller.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.portfolio.PortfolioFeedbackRequest;
import start.capstone2.dto.portfolio.PortfolioFunctionRequest;
import start.capstone2.service.portfolio.PortfolioFeedbackService;
import start.capstone2.service.portfolio.PortfolioFunctionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio/function")
public class PortfolioFunctionController {

    private final PortfolioFunctionService functionService;

    @PostMapping("/")
    public Long createFeedback(Long userId, Long portfolioId, PortfolioFunctionRequest request) {
        return functionService.createPortfolioFunction(userId, portfolioId, request);
    }

    @PutMapping("/{functionId}")
    public void updateFeedback(Long userId, Long portfolioId, @PathVariable Long functionId, PortfolioFunctionRequest request) {
        functionService.updatePortfolioFunction(userId, portfolioId, functionId, request);
    }

    @DeleteMapping("/{functionId}")
    public void deleteFeedback(Long userId, Long portfolioId, @PathVariable Long functionId) {
        functionService.deletePortfolioComment(userId, portfolioId, functionId);
    }

}
