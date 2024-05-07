package start.capstone2.controller.portfolio;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import start.capstone2.domain.portfolio.dto.PortfolioCommentRequest;
import start.capstone2.service.portfolio.PortfolioCommentService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio/comment")
public class PortfolioCommentController {

    private final PortfolioCommentService commentService;

    @PostMapping("/")
    public Long createPortfolioComment(Long userId, Long portfolioId, PortfolioCommentRequest request) {
        return commentService.createPortfolioComment(userId, portfolioId, request);
    }

    @PutMapping("/{commentId}")
    public void updatePortfolioComment(Long userId, Long portfolioId, @PathVariable Long commentId, PortfolioCommentRequest request) {
        commentService.updatePortfolioComment(userId, portfolioId, commentId, request);
    }

    @DeleteMapping("/{commentId}")
    public void deletePortfolioComment(Long userId, Long portfolioId, @PathVariable Long commentId) {
        commentService.deletePortfolioComment(userId, portfolioId, commentId);
    }
}
