package start.capstone2.controller.portfolio;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioUrlRequest;
import start.capstone2.dto.portfolio.PortfolioUrlResponse;
import start.capstone2.service.portfolio.PortfolioUrlService;

import java.util.List;

@Tag(name = "PortfolioUrl api", description = "[미완] 포트폴리오 공유 url 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioUrlController {

    private final PortfolioUrlService urlService;

    @Operation(summary = "create portfolio tech-stack", description = "선택한 여러개의 techstack을 portfolio에 등록(생성)")
    @PostMapping("/{portfolioId}/url")
    public Long createPortfolioUrl(Long userId, @PathVariable Long portfolioId, PortfolioUrlRequest request) {
        return urlService.createPortfolioUrl(userId, portfolioId, request.getUrlId());
    }

    @Operation(summary = "find all portfolio tech-stack", description = "해당 portfolio의 모든 tech-stack 조회")
    @GetMapping("/{portfolioId}/url")
    public ResponseResult<List<PortfolioUrlResponse>> findAllPortfolioUrl(Long userId, @PathVariable Long portfolioId) {
        return new ResponseResult<>(urlService.findAllByPortfolioId(userId, portfolioId));
    }

    @Operation(summary = "delete portfolio tech-stack", description = "해당 portfolio에서 techstack 단일 삭제")
    @DeleteMapping("/{portfolioId}/url/{urlId}")
    public void deletePortfolioUrl(Long userId, @PathVariable Long portfolioId, @PathVariable Long urlId) {
        urlService.deletePortfolioUrl(userId, portfolioId, urlId, null);
    }

}
