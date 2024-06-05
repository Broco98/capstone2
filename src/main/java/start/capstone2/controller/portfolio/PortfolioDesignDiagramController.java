package start.capstone2.controller.portfolio;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.domain.portfolio.PortfolioDesignDiagram;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioDesignDiagramRequest;
import start.capstone2.dto.portfolio.PortfolioDesignRequest;
import start.capstone2.dto.portfolio.PortfolioDesignResponse;
import start.capstone2.service.portfolio.PortfolioDesignDiagramService;
import start.capstone2.service.portfolio.PortfolioDesignService;

import java.util.List;

@Tag(name="Portfolio Design Diagram api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio-design")
public class PortfolioDesignDiagramController {

    private final PortfolioDesignDiagramService diagramService;

    @Operation(summary = "create portfolio design diagram")
    @PostMapping("/{designId}/diagram")
    public Long createPortfolioDesign(HttpServletRequest servletRequest, @PathVariable Long designId, PortfolioDesignDiagramRequest request) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        return diagramService.createPortfolioDesignDiagram(userId, designId, request);
    }
    
    @Operation(summary = "update portfolio design diagram")
    @PutMapping("/{designId}/diagram/{diagramId}")
    public void updatePortfolioDesign(HttpServletRequest servletRequest, @PathVariable Long designId, @PathVariable Long diagramId, PortfolioDesignDiagramRequest request) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        diagramService.updatePortfolioDesignSchema(userId, designId, diagramId, request);
    }

    @Operation(summary = "delete portfolio design diagram")
    @DeleteMapping("/{designId}/diagram/{diagramId}")
    public void deletePortfolioPortfolioDesign(HttpServletRequest servletRequest, @PathVariable Long designId, @PathVariable Long c) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        diagramService.deletePortfolioDesignDiagram(userId, designId, designId);
    }
    
//    @Operation(summary = "find all portfolio design", description = "포트폴리오의 모든 설계 조회")
//    @GetMapping("/{portfolioId}/design")
//    public ResponseResult<List<PortfolioDesignResponse>> findAllPortfolioDesign(Long userId, @PathVariable Long portfolioId) {
//        List<PortfolioDesignResponse> result = designService.findPortfolioDesigns(userId, portfolioId);
//        return new ResponseResult<>(result);
//    }

}
