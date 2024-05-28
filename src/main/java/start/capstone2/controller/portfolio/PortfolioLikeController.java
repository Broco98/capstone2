package start.capstone2.controller.portfolio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.service.portfolio.PortfolioLikeService;

@Tag(name = "PortfolioLike api", description = "포트폴리오 좋아요 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioLikeController {

    private final PortfolioLikeService likeService;

    @Operation(summary = "create portfolio_like", description = "portfolio 하나를 좋아요 합니다.")
    @PostMapping("/{portfolioId}/like")
    public Long createPortfolioLike(Long userId, @PathVariable Long portfolioId) {
        return likeService.createPortfolioLike(userId, portfolioId);
    }

    @Operation(summary = "delete portfolio_like", description = "portfolio 좋아요 삭제(취소).")
    @DeleteMapping("/{portfolioId}/like/{likeId}")
    public void deletePortfolioLile(Long userId, @PathVariable Long portfolioId, @PathVariable Long likeId) {
        likeService.deletePortfolioLike(userId, likeId);
    }


}
