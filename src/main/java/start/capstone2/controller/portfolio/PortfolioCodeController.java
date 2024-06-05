package start.capstone2.controller.portfolio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioCodeRequest;
import start.capstone2.dto.portfolio.PortfolioCodeResponse;
import start.capstone2.service.portfolio.PortfolioCodeService;

import java.util.List;

@Tag(name="Portfolio Code Api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioCodeController {

    private final PortfolioCodeService codeService;

    @Operation(summary = "create portfolio code")
    @PostMapping("/{portfolioId}/code")
    public Long createPortfolioCode(Long userId, @PathVariable Long portfolioId, PortfolioCodeRequest request) {
        return codeService.createPortfolioCode(userId, portfolioId, request);
    }
    
    @Operation(summary = "update portfolio code")
    @PutMapping("/{portfolioId}/code/{codeId}")
    public void updatePortfolioCode(Long userId, @PathVariable Long portfolioId, @PathVariable Long codeId, PortfolioCodeRequest request) {
        codeService.updatePortfolioCode(userId, portfolioId, codeId, request);
    }

    @Operation(summary = "delete portfolio code")
    @DeleteMapping("/{portfolioId}/code/{codeId}")
    public void deletePortfolioCode(Long userId, @PathVariable Long portfolioId, @PathVariable Long codeId) {
        codeService.deletePortfolioCode(userId, portfolioId, codeId);
    }

    @Operation(summary = "find all portfolio code")
    @GetMapping("/{portfolioId}/code")
    public ResponseResult<List<PortfolioCodeResponse>> findAllPortfolioCode(Long userId, @PathVariable Long portfolioId) {
        List<PortfolioCodeResponse> result = codeService.findPortfolioCodes(userId, portfolioId);
        return new ResponseResult<>(result);
    }

}
