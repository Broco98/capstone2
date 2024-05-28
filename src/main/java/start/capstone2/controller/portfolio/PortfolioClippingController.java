package start.capstone2.controller.portfolio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioApiRequest;
import start.capstone2.dto.portfolio.PortfolioApiResponse;
import start.capstone2.service.portfolio.PortfolioApiService;
import start.capstone2.service.portfolio.PortfolioClippingService;

import java.util.List;

@Tag(name = "PortfolioClipping api", description = "portfolio 스크랩 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioClippingController {

    private final PortfolioClippingService clippingService;

    @Operation(summary = "create portfolio_clipping", description = "portfolio 하나를 스크랩 합니다.")
    @PostMapping("/{portfolioId}/clipping")
    public Long createPortfolioClipping(Long userId, @PathVariable Long portfolioId) {
        return clippingService.createPortfolioClipping(userId, portfolioId);
    }

    @Operation(summary = "delete portfolio_clipping", description = "portfolio 스크랩츨 삭제(취소) 합니다.")
    @DeleteMapping("/{portfolioId}/clipping/{clippingId}")
    public void deletePortfolioClipping(Long userId, @PathVariable Long portfolioId, @PathVariable Long clippingId) {
        clippingService.deletePortfolioClipping(userId, clippingId);
    }

}
