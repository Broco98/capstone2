package start.capstone2.controller.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioCodeRequest;
import start.capstone2.dto.portfolio.PortfolioCodeResponse;
import start.capstone2.service.portfolio.PortfolioCodeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioCodeController {

    private final PortfolioCodeService codeService;

    @PostMapping("/{portfolioId}/code")
    public Long createPortfolioCode(Long userId, @PathVariable Long portfolioId, PortfolioCodeRequest request) {
        return codeService.createPortfolioCode(userId, portfolioId, request);
    }

    @PutMapping("/{portfolioId}/code/{codeId}")
    public void updatePortfolioCode(Long userId, @PathVariable Long portfolioId, @PathVariable Long codeId, PortfolioCodeRequest request) {
        codeService.updatePortfolioCode(userId, portfolioId, codeId, request);
    }

    @DeleteMapping("/{portfolioId}/code/{codeId}")
    public void deletePortfolioCode(Long userId, @PathVariable Long portfolioId, @PathVariable Long codeId) {
        codeService.deletePortfolioCode(userId, portfolioId, codeId);
    }

    @GetMapping("/{portfolioId}/code")
    public ResponseResult<List<PortfolioCodeResponse>> findAllPortfolioCode(Long userId, @PathVariable Long portfolioId) {
        List<PortfolioCodeResponse> result = codeService.findPortfolioCodes(userId, portfolioId);
        return new ResponseResult<>(result);
    }

}
