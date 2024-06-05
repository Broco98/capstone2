package start.capstone2.controller.portfolio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
    public Long createPortfolioCode(@PathVariable Long portfolioId, PortfolioCodeRequest request, HttpServletRequest servletRequest) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        return codeService.createPortfolioCode(userId, portfolioId, request);
    }
    
    @Operation(summary = "update portfolio code")
    @PutMapping("/{portfolioId}/code/{codeId}")
    public void updatePortfolioCode(@PathVariable Long portfolioId, @PathVariable Long codeId, PortfolioCodeRequest request, HttpServletRequest servletRequest) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        codeService.updatePortfolioCode(userId, portfolioId, codeId, request);
    }

    @Operation(summary = "delete portfolio code")
    @DeleteMapping("/{portfolioId}/code/{codeId}")
    public void deletePortfolioCode(@PathVariable Long portfolioId, @PathVariable Long codeId, HttpServletRequest servletRequest) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        codeService.deletePortfolioCode(userId, portfolioId, codeId);
    }

    @Operation(summary = "find all portfolio code")
    @GetMapping("/{portfolioId}/code")
    public ResponseResult<List<PortfolioCodeResponse>> findAllPortfolioCode(@PathVariable Long portfolioId, HttpServletRequest servletRequest) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        List<PortfolioCodeResponse> result = codeService.findPortfolioCodes(userId, portfolioId);
        return new ResponseResult<>(result);
    }

}
