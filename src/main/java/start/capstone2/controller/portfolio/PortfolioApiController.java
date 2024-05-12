package start.capstone2.controller.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.portfolio.PortfolioCommentRequest;
import start.capstone2.service.portfolio.PortfolioCommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio/api")
public class PortfolioApiController {

    private final PortfolioCommentService commentService;

    @PostMapping("/")
    public Long createPortfolioApi(Long userId, Long portfolioId, PortfolioCommentRequest request) {
        return commentService.createPortfolioComment(userId, portfolioId, request);
    }

    @PutMapping("/{apiId}")
    public void updatePortfolioApi(Long userId, Long portfolioId, @PathVariable Long apiId, PortfolioCommentRequest request) {
        commentService.updatePortfolioComment(userId, portfolioId, apiId, request);
    }

    @DeleteMapping("/{apiId}")
    public void deletePortfolioApi(Long userId, Long portfolioId, @PathVariable Long apiId) {
        commentService.deletePortfolioComment(userId, portfolioId, apiId);
    }

}
