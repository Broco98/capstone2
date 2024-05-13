package start.capstone2.controller.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioFeedbackRequest;
import start.capstone2.dto.portfolio.PortfolioFeedbackResponse;
import start.capstone2.service.portfolio.PortfolioFeedbackService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioFeedbackController {

    private final PortfolioFeedbackService feedbackService;

    @PostMapping("/{portfolioId}/feedback")
    public Long createPortfolioFeedback(Long userId, @PathVariable Long portfolioId, PortfolioFeedbackRequest request) {
        return feedbackService.createPortfolioFeedback(userId, portfolioId, request);
    }

    @PutMapping("/{portfolioId}/feedback/{feedbackId}")
    public void updatePortfolioFeedback(Long userId, @PathVariable Long portfolioId, @PathVariable Long feedbackId, PortfolioFeedbackRequest request) {
        feedbackService.updatePortfolioFeedback(userId, portfolioId, feedbackId, request);
    }

    @DeleteMapping("/{portfolioId}/feedback/{feedbackId}")
    public void deletePortfolioFeedback(Long userId, @PathVariable Long portfolioId, @PathVariable Long feedbackId) {
        feedbackService.deletePortfolioFeedback(userId, portfolioId, feedbackId);
    }

    @GetMapping("/{portfolioId}/feedback")
    public ResponseResult<List<PortfolioFeedbackResponse>> findAllPortfolioFeedback(Long userId, @PathVariable Long portfolioId) {
        List<PortfolioFeedbackResponse> result = feedbackService.findPortfolioFeedbacks(userId, portfolioId);
        return new ResponseResult<>(result);
    }

}
