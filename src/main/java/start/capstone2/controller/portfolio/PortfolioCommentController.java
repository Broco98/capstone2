package start.capstone2.controller.portfolio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioCommentRequest;
import start.capstone2.dto.portfolio.PortfolioCommentResponse;
import start.capstone2.service.portfolio.PortfolioCommentService;

import java.util.List;

@Tag(name="PortfolioComment api", description = "portfolio 댓글 관리")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioCommentController {

    private final PortfolioCommentService commentService;

    @Operation(summary = "create portfolio_comment", description = "해당 포트폴리오에 댓글 하나를 생성합니다.")
    @PostMapping("/{portfolioId}/comment")
    public Long createPortfolioComment(Long userId, @PathVariable Long portfolioId, PortfolioCommentRequest request) {
        return commentService.createPortfolioComment(userId, portfolioId, request);
    }
    
    @Operation(summary = "update portfolio_comment", description = "포트폴리오 댓글 하나를 수정합니다")
    @PutMapping("/{portfolioId}/comment/{commentId}")
    public void updatePortfolioComment(Long userId, @PathVariable Long portfolioId, @PathVariable Long commentId, PortfolioCommentRequest request) {
        commentService.updatePortfolioComment(userId, portfolioId, request);
    }

    @Operation(summary = "delete portfolio_comment", description = "포트폴리오 댓글 하나를 삭제합니다.")
    @DeleteMapping("/{portfolioId}/comment/{commentId}")
    public void deletePortfolioComment(Long userId, @PathVariable Long portfolioId, @PathVariable Long commentId) {
        commentService.deletePortfolioComment(userId, portfolioId, commentId);
    }

    @Operation(summary = "find all portfolio_comment", description = "포트폴리오에 있는 모든 댓글을 조회합니다.")
    @GetMapping("/{portfolioId}/comment")
    public ResponseResult<List<PortfolioCommentResponse>> findAllPortfolioComment(Long userId, @PathVariable Long portfolioId) {
        List<PortfolioCommentResponse> result = commentService.findPortfolioComments(userId, portfolioId);
        return new ResponseResult<>(result);
    }
}
