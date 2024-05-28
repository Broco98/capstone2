package start.capstone2.controller.portfolio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioFeedbackRequest;
import start.capstone2.dto.portfolio.PortfolioFeedbackResponse;
import start.capstone2.service.portfolio.PortfolioFeedbackService;

import java.util.List;

@Tag(name = "PortfolioFeedback api", description = "portfolio 피드백 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioFeedbackController {

    private final PortfolioFeedbackService feedbackService;

    @Operation(summary = "create portfolio feedback", description = "portfolio 피드백 단일 생성")
    @PostMapping("/{portfolioId}/feedback")
    public Long createPortfolioFeedback(Long userId, @PathVariable Long portfolioId, PortfolioFeedbackRequest request) {
        return feedbackService.createPortfolioFeedback(userId, portfolioId, request);
    }

    @Operation(summary = "update portfolio feedback", description = "portfolio 피드백 단일 수정")
    @PutMapping("/{portfolioId}/feedback/{feedbackId}")
    public void updatePortfolioFeedback(Long userId, @PathVariable Long portfolioId, @PathVariable Long feedbackId, PortfolioFeedbackRequest request) {
        feedbackService.updatePortfolioFeedback(userId, feedbackId, request);
    }
    
    @Operation(summary = "delete portfolio feedback", description = "portfolio 피드백 삭제")
    @DeleteMapping("/{portfolioId}/feedback/{feedbackId}")
    public void deletePortfolioFeedback(Long userId, @PathVariable Long portfolioId, @PathVariable Long feedbackId) {
        feedbackService.deletePortfolioFeedback(userId, portfolioId, feedbackId);
    }

    @Operation(summary = "find all portfolio feedback", description = "선택한 portfolio의 모든 feedback 조회")
    @GetMapping("/{portfolioId}/feedback")
    public ResponseResult<List<PortfolioFeedbackResponse>> findAllPortfolioFeedback(Long userId, @PathVariable Long portfolioId) {
        List<PortfolioFeedbackResponse> result = feedbackService.findPortfolioFeedbacks(userId, portfolioId);
        return new ResponseResult<>(result);
    }

}
