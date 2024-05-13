package start.capstone2.controller.portfolio;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioDesignRequest;
import start.capstone2.dto.portfolio.PortfolioDesignResponse;
import start.capstone2.service.portfolio.PortfolioDesignService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioDesignController {

    private final PortfolioDesignService designService;

    @PostMapping("/{portfolioId}/design")
    public Long createPortfolioDesign(Long userId, @PathVariable Long portfolioId, PortfolioDesignRequest request) {
        return designService.createPortfolioDesign(userId, portfolioId, request);
    }

    @PutMapping("/{portfolioId}/design/{designId}")
    public void updatePortfolioDesign(Long userId, @PathVariable Long portfolioId, @PathVariable Long designId, PortfolioDesignRequest request) {
        designService.updatePortfolioDesign(userId, portfolioId, designId, request);
    }

    @DeleteMapping("/{portfolioId}/design/{designId}")
    public void deletePortfolioPortfolioDesign(Long userId, @PathVariable Long portfolioId, @PathVariable Long designId) {
        designService.deletePortfolioDesign(userId, portfolioId, designId);
    }

    @GetMapping("/{portfolioId}/design")
    public ResponseResult<List<PortfolioDesignResponse>> findAllPortfolioDesign(Long userId, @PathVariable Long portfolioId) {
        List<PortfolioDesignResponse> result = designService.findPortfolioDesigns(userId, portfolioId);
        return new ResponseResult<>(result);
    }

}
