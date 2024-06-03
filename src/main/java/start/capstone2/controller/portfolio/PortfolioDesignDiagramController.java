package start.capstone2.controller.portfolio;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public Long createPortfolioDesign(Long userId, @PathVariable Long designId, PortfolioDesignDiagramRequest request) {
        return diagramService.createPortfolioDesignDiagram(userId, designId, request);
    }
    
    @Operation(summary = "update portfolio design diagram")
    @PutMapping("/{designId}/diagram/{diagramId}")
    public void updatePortfolioDesign(Long userId, @PathVariable Long designId, @PathVariable Long diagramId, PortfolioDesignDiagramRequest request) {
        diagramService.updatePortfolioDesignSchema(userId, designId, diagramId, request);
    }

    @Operation(summary = "delete portfolio design diagram")
    @DeleteMapping("/{designId}/diagram/{diagramId}")
    public void deletePortfolioPortfolioDesign(Long userId, @PathVariable Long designId, @PathVariable Long c) {
        diagramService.deletePortfolioDesignDiagram(userId, designId, designId);
    }
    
//    @Operation(summary = "find all portfolio design", description = "포트폴리오의 모든 설계 조회")
//    @GetMapping("/{portfolioId}/design")
//    public ResponseResult<List<PortfolioDesignResponse>> findAllPortfolioDesign(Long userId, @PathVariable Long portfolioId) {
//        List<PortfolioDesignResponse> result = designService.findPortfolioDesigns(userId, portfolioId);
//        return new ResponseResult<>(result);
//    }

}
