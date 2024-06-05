package start.capstone2.controller.portfolio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioFeedbackRequest;
import start.capstone2.dto.portfolio.PortfolioFeedbackResponse;
import start.capstone2.service.portfolio.PortfolioFeedbackService;

import java.util.List;

@Tag(name = "Portfolio Feedback Api", description = "portfolio 피드백 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioFeedbackController {

    private final PortfolioFeedbackService feedbackService;

    @Operation(summary = "create portfolio feedback")
    @PostMapping("/{portfolioId}/feedback")
    public Long createPortfolioFeedback(HttpServletRequest servletRequest, @PathVariable Long portfolioId, PortfolioFeedbackRequest request) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        return feedbackService.createPortfolioFeedback(userId, portfolioId, request);
    }

    @Operation(summary = "update portfolio feedback")
    @PutMapping("/{portfolioId}/feedback/{feedbackId}")
    public void updatePortfolioFeedback(HttpServletRequest servletRequest, @PathVariable Long portfolioId, @PathVariable Long feedbackId, PortfolioFeedbackRequest request) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        feedbackService.updatePortfolioFeedback(userId, portfolioId, feedbackId, request);
    }
    
    @Operation(summary = "delete portfolio feedback")
    @DeleteMapping("/{portfolioId}/feedback/{feedbackId}")
    public void deletePortfolioFeedback(HttpServletRequest servletRequest, @PathVariable Long portfolioId, @PathVariable Long feedbackId) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        feedbackService.deletePortfolioFeedback(userId, portfolioId, feedbackId);
    }

    @Operation(summary = "find all portfolio feedback")
    @GetMapping("/{portfolioId}/feedback")
    public ResponseResult<List<PortfolioFeedbackResponse>> findAllPortfolioFeedback(HttpServletRequest servletRequest, @PathVariable Long portfolioId) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        List<PortfolioFeedbackResponse> result = feedbackService.findPortfolioFeedbacks(userId, portfolioId);
        return new ResponseResult<>(result);
    }

}
