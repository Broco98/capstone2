package start.capstone2.controller.portfolio;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioCommentRequest;
import start.capstone2.dto.portfolio.PortfolioCommentResponse;
import start.capstone2.service.portfolio.PortfolioCommentService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioCommentController {

    private final PortfolioCommentService commentService;

    @PostMapping("/{portfolioId}/comment")
    public Long createPortfolioComment(Long userId, @PathVariable Long portfolioId, PortfolioCommentRequest request) {
        return commentService.createPortfolioComment(userId, portfolioId, request);
    }

    @PutMapping("/{portfolioId}/comment/{commentId}")
    public void updatePortfolioComment(Long userId, @PathVariable Long portfolioId, @PathVariable Long commentId, PortfolioCommentRequest request) {
        commentService.updatePortfolioComment(userId, portfolioId, commentId, request);
    }

    @DeleteMapping("/{portfolioId}/comment/{commentId}")
    public void deletePortfolioComment(Long userId, @PathVariable Long portfolioId, @PathVariable Long commentId) {
        commentService.deletePortfolioComment(userId, portfolioId, commentId);
    }

    @GetMapping("/{portfolioId}/comment")
    public ResponseResult<List<PortfolioCommentResponse>> findAllPortfolioComment(Long userId, @PathVariable Long portfolioId) {
        List<PortfolioCommentResponse> result = commentService.findPortfolioComments(userId, portfolioId);
        return new ResponseResult<>(result);
    }
}
