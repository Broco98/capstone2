package start.capstone2.controller.portfolio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioCommentRequest;
import start.capstone2.dto.portfolio.PortfolioCommentResponse;
import start.capstone2.service.portfolio.PortfolioCommentService;

import java.util.List;

@Tag(name="Portfolio Comment Api", description = "portfolio 댓글 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioCommentController {

    private final PortfolioCommentService commentService;

    @Operation(summary = "create portfolio comment")
    @PostMapping("/{portfolioId}/comment")
    public Long createPortfolioComment(@PathVariable Long portfolioId, PortfolioCommentRequest request, HttpServletRequest servletRequest) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        return commentService.createPortfolioComment(userId, portfolioId, request);
    }
    
    @Operation(summary = "update portfolio comment")
    @PutMapping("/{portfolioId}/comment/{commentId}")
    public void updatePortfolioComment(@PathVariable Long portfolioId, @PathVariable Long commentId, PortfolioCommentRequest request, HttpServletRequest servletRequest) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        commentService.updatePortfolioComment(userId, portfolioId, commentId, request);
    }

    @Operation(summary = "delete portfolio comment")
    @DeleteMapping("/{portfolioId}/comment/{commentId}")
    public void deletePortfolioComment(@PathVariable Long portfolioId, @PathVariable Long commentId, HttpServletRequest servletRequest) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        commentService.deletePortfolioComment(userId, portfolioId, commentId);
    }

    @Operation(summary = "find all portfolio comment")
    @GetMapping("/{portfolioId}/comment")
    public ResponseResult<List<PortfolioCommentResponse>> findAllPortfolioComment(@PathVariable Long portfolioId, HttpServletRequest servletRequest) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        List<PortfolioCommentResponse> result = commentService.findPortfolioComments(userId, portfolioId);
        return new ResponseResult<>(result);
    }
}
