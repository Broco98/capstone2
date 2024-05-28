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

@Tag(name="PortfolioCode api", description = "portfolio code 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioCodeController {

    private final PortfolioCodeService codeService;

    @Operation(summary = "create portfolio_code", description = "portfolio code 하나 생성")
    @PostMapping("/{portfolioId}/code")
    public Long createPortfolioCode(Long userId, @PathVariable Long portfolioId, PortfolioCodeRequest request) {
        return codeService.createPortfolioCode(userId, portfolioId, request);
    }
    
    @Operation(summary = "update portfolio_code", description = "portfolio code 하나 수정")
    @PutMapping("/{portfolioId}/code/{codeId}")
    public void updatePortfolioCode(Long userId, @PathVariable Long portfolioId, @PathVariable Long codeId, PortfolioCodeRequest request) {
        codeService.updatePortfolioCode(userId, portfolioId, codeId, request);
    }

    @Operation(summary = "delete portfolio_code", description = "portfolio code 하나 삭제")
    @DeleteMapping("/{portfolioId}/code/{codeId}")
    public void deletePortfolioCode(Long userId, @PathVariable Long portfolioId, @PathVariable Long codeId) {
        codeService.deletePortfolioCode(userId, portfolioId, codeId);
    }

    @Operation(summary = "find all portfolio_code", description = "portfolio에 있는 모든 portfolio_code 조회")
    @GetMapping("/{portfolioId}/code")
    public ResponseResult<List<PortfolioCodeResponse>> findAllPortfolioCode(Long userId, @PathVariable Long portfolioId) {
        List<PortfolioCodeResponse> result = codeService.findPortfolioCodes(userId, portfolioId);
        return new ResponseResult<>(result);
    }

}
