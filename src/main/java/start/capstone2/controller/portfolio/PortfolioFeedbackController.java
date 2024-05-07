package start.capstone2.controller.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.domain.portfolio.dto.PortfolioFeedbackRequest;
import start.capstone2.service.portfolio.PortfolioFeedbackService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio/feedback")
public class PortfolioFeedbackController {

    private final PortfolioFeedbackService portfolioFeedbackService;

    @PostMapping("/")
    public Long createFeedback(Long userId, Long portfolioId, PortfolioFeedbackRequest request) {
        return portfolioFeedbackService.createPortfolioFeedback(userId, portfolioId, request);
    }

    @PutMapping("/{feedbackId}")
    public void updateFeedback(Long userId, Long portfolioId, @PathVariable Long feedbackId, PortfolioFeedbackRequest request) {
        portfolioFeedbackService.updatePortfolioFeedback(userId, portfolioId, feedbackId, request);
    }

    @DeleteMapping("/{feedbackId}")
    public void deleteFeedback(Long userId, Long portfolioId, @PathVariable Long feedbackId) {
        portfolioFeedbackService.deletePortfolioFeedback(userId, portfolioId, feedbackId);
    }

}
