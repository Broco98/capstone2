package start.capstone2.controller.portfolio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.service.portfolio.PortfolioClippingService;

@Tag(name = "Portfolio Clipping Api", description = "portfolio 스크랩 관련 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioClippingController {

    private final PortfolioClippingService clippingService;

    @Operation(summary = "create portfolio clipping", description = "선택한 portfolio를 스크랩합니다.")
    @PostMapping("/{portfolioId}/clipping")
    public Long createPortfolioClipping(@PathVariable Long portfolioId, HttpServletRequest servletRequest) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        return clippingService.createPortfolioClipping(userId, portfolioId);
    }

    @Operation(summary = "delete portfolio clipping", description = "선택한 portfolio의 스크랩을 취소합니다.")
    @DeleteMapping("/{portfolioId}/clipping/{clippingId}")
    public void deletePortfolioClipping(@PathVariable Long portfolioId, @PathVariable Long clippingId, HttpServletRequest servletRequest) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        clippingService.deletePortfolioClipping(userId, clippingId);
    }

}
