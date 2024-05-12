package start.capstone2.controller.portfolio;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.portfolio.PortfolioCodeRequest;
import start.capstone2.dto.portfolio.PortfolioDesignRequest;
import start.capstone2.service.portfolio.PortfolioDesignService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio/design")
public class PortfolioDesignController {

    private final PortfolioDesignService designService;

    @PostMapping("/")
    public Long createPortfolioDesign(Long userId, Long portfolioId, PortfolioDesignRequest request) {
        return designService.createPortfolioDesign(userId, portfolioId, request);
    }

    @PutMapping("/{designId}")
    public void updatePortfolioDesign(Long userId, Long portfolioId, @PathVariable Long designId, PortfolioDesignRequest request) {
        designService.updatePortfolioDesign(userId, portfolioId, designId, request);
    }

    @DeleteMapping("/{designId}")
    public void deletePortfolioPortfolioDesign(Long userId, Long portfolioId, @PathVariable Long designId) {
        designService.deletePortfolioComment(userId, portfolioId, designId);
    }


}
