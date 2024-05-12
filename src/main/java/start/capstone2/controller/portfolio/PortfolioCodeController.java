package start.capstone2.controller.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.portfolio.PortfolioCodeRequest;
import start.capstone2.dto.portfolio.PortfolioCommentRequest;
import start.capstone2.service.portfolio.PortfolioCodeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio/code")
public class PortfolioCodeController {

    private final PortfolioCodeService codeService;

    @PostMapping("/")
    public Long createPortfolioCode(Long userId, Long portfolioId, PortfolioCodeRequest request) {
        return codeService.createPortfolioCode(userId, portfolioId, request);
    }

    @PutMapping("/{codeId}")
    public void updatePortfolioCode(Long userId, Long portfolioId, @PathVariable Long codeId, PortfolioCodeRequest request) {
        codeService.updatePortfolioCode(userId, portfolioId, codeId, request);
    }

    @DeleteMapping("/{codeId}")
    public void deletePortfolioCode(Long userId, Long portfolioId, @PathVariable Long codeId) {
        codeService.deletePortfolioCode(userId, portfolioId, codeId);
    }

}
