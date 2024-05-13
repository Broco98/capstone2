package start.capstone2.controller.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioFunctionRequest;
import start.capstone2.dto.portfolio.PortfolioFunctionResponse;
import start.capstone2.service.portfolio.PortfolioFunctionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioFunctionController {

    private final PortfolioFunctionService functionService;

    @PostMapping("/{portfolioId}/function")
    public Long createPortfolioFunction(Long userId, @PathVariable Long portfolioId, PortfolioFunctionRequest request) {
        return functionService.createPortfolioFunction(userId, portfolioId, request);
    }

    @PutMapping("/{portfolioId}/function/{functionId}")
    public void updatePortfolioFunction(Long userId, @PathVariable Long portfolioId, @PathVariable Long functionId, PortfolioFunctionRequest request) {
        functionService.updatePortfolioFunction(userId, portfolioId, functionId, request);
    }

    @DeleteMapping("/{portfolioId}/function/{functionId}")
    public void deletePortfolioFunction(Long userId, @PathVariable Long portfolioId, @PathVariable Long functionId) {
        functionService.deletePortfolioFunction(userId, portfolioId, functionId);
    }

    @GetMapping("/{portfolioId}/function")
    public ResponseResult<List<PortfolioFunctionResponse>> findAllPortfolioFunction(Long userId, @PathVariable Long portfolioId) {
        List<PortfolioFunctionResponse> result = functionService.findPortfolioFunctions(userId, portfolioId);
        return new ResponseResult<>(result);
    }
}
