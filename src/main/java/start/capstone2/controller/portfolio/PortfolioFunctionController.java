package start.capstone2.controller.portfolio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioFunctionRequest;
import start.capstone2.dto.portfolio.PortfolioFunctionResponse;
import start.capstone2.service.portfolio.PortfolioFunctionService;

import java.util.List;

@Tag(name = "Portfolio Function Api", description = "portfolio 기능 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioFunctionController {

    private final PortfolioFunctionService functionService;
    
    @Operation(summary = "create portfolio function")
    @PostMapping("/{portfolioId}/function")
    public Long createPortfolioFunction(Long userId, @PathVariable Long portfolioId, PortfolioFunctionRequest request) {
        return functionService.createPortfolioFunction(userId, portfolioId, request);
    }
    
    @Operation(summary = "update portfolio function")
    @PutMapping("/{portfolioId}/function/{functionId}")
    public void updatePortfolioFunction(Long userId, @PathVariable Long portfolioId, @PathVariable Long functionId, PortfolioFunctionRequest request) {
        functionService.updatePortfolioFunction(userId, portfolioId, functionId, request);
    }

    @Operation(summary = "delete portfolio function")
    @DeleteMapping("/{portfolioId}/function/{functionId}")
    public void deletePortfolioFunction(Long userId, @PathVariable Long portfolioId, @PathVariable Long functionId) {
        functionService.deletePortfolioFunction(userId, portfolioId, functionId);
    }

    @Operation(summary = "find all portfolio function")
    @GetMapping("/{portfolioId}/function")
    public ResponseResult<List<PortfolioFunctionResponse>> findAllPortfolioFunction(Long userId, @PathVariable Long portfolioId) {
        List<PortfolioFunctionResponse> result = functionService.findPortfolioFunctions(userId, portfolioId);
        return new ResponseResult<>(result);
    }

}
