package start.capstone2.controller.portfolio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.service.portfolio.PortfolioLikeService;

@Tag(name = "Portfolio Like Api", description = "포트폴리오 좋아요 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioLikeController {

    private final PortfolioLikeService likeService;

    @Operation(summary = "create portfolio like", description = "선택한 portfolio 하나를 좋아요에 추가")
    @PostMapping("/{portfolioId}/like")
    public Long createPortfolioLike(HttpServletRequest servletRequest, @PathVariable Long portfolioId) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        return likeService.createPortfolioLike(userId, portfolioId);
    }
    
    // TODO 좀 더 고민해보자
    @Operation(summary = "delete portfolio like", description = "선택한 portfolio 좋아요 취소")
    @DeleteMapping("/{portfolioId}/like/{likeId}")
    public void deletePortfolioLike(HttpServletRequest servletRequest, @PathVariable Long portfolioId, @PathVariable Long likeId) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        likeService.deletePortfolioLike(userId, likeId);
    }
    
}
