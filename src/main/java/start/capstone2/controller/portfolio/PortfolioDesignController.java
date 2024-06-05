package start.capstone2.controller.portfolio;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioDesignRequest;
import start.capstone2.dto.portfolio.PortfolioDesignResponse;
import start.capstone2.service.portfolio.PortfolioDesignService;

import java.util.List;

@Tag(name="Portfolio Design Api", description = "portfolio 설계 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioDesignController {

    private final PortfolioDesignService designService;

    @Operation(summary = "create portfolio design", description = "포트폴리오 설계 단일 생성")
    @PostMapping("/{portfolioId}/design")
    public Long createPortfolioDesign(HttpServletRequest servletRequest, @PathVariable Long portfolioId, PortfolioDesignRequest request) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        return designService.createPortfolioDesign(userId, portfolioId, request);
    }
    
    @Operation(summary = "update portfolio design", description = "포트폴리오 설계 수정")
    @PutMapping("/{portfolioId}/design/{designId}")
    public void updatePortfolioDesign(HttpServletRequest servletRequest, @PathVariable Long portfolioId, @PathVariable Long designId, PortfolioDesignRequest request) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        designService.updatePortfolioDesign(userId, portfolioId, designId, request);
    }

    @Operation(summary = "delete portfolio design", description = "포트폴리오 설계 단일 삭제")
    @DeleteMapping("/{portfolioId}/design/{designId}")
    public void deletePortfolioPortfolioDesign(HttpServletRequest servletRequest, @PathVariable Long portfolioId, @PathVariable Long designId) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        designService.deletePortfolioDesign(userId, portfolioId, designId);
    }
    
    @Operation(summary = "find all portfolio design", description = "포트폴리오의 모든 설계 조회")
    @GetMapping("/{portfolioId}/design")
    public ResponseResult<List<PortfolioDesignResponse>> findAllPortfolioDesign(HttpServletRequest servletRequest, @PathVariable Long portfolioId) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        List<PortfolioDesignResponse> result = designService.findPortfolioDesigns(userId, portfolioId);
        return new ResponseResult<>(result);
    }

}
