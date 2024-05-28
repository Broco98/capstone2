package start.capstone2.controller.portfolio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioFunctionRequest;
import start.capstone2.dto.portfolio.PortfolioFunctionResponse;
import start.capstone2.service.portfolio.PortfolioFunctionModuleService;
import start.capstone2.service.portfolio.PortfolioFunctionService;

import java.util.List;

@Tag(name = "PortfolioFunction api", description = "포트폴리오 기능 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioFunctionController {

    private final PortfolioFunctionService functionService;
    
    @Operation(summary = "create portfolio function", description = "포트폴리오 기능 명세 생성")
    @PostMapping("/{portfolioId}/function")
    public Long createPortfolioFunction(Long userId, @PathVariable Long portfolioId, PortfolioFunctionRequest request) {
        return functionService.createPortfolioFunction(userId, portfolioId, request);
    }
    
    @Operation(summary = "update portfolio function", description = "포트폴리오 기능 명세 수정")
    @PutMapping("/{portfolioId}/function/{functionId}")
    public void updatePortfolioFunction(Long userId, @PathVariable Long portfolioId, @PathVariable Long functionId, PortfolioFunctionRequest request) {
        functionService.updatePortfolioFunction(userId, portfolioId, functionId, request);
    }

    @Operation(summary = "delete portfolio function", description = "포트폴리오 기능 명세 삭제")
    @DeleteMapping("/{portfolioId}/function/{functionId}")
    public void deletePortfolioFunction(Long userId, @PathVariable Long portfolioId, @PathVariable Long functionId) {
        functionService.deletePortfolioFunction(userId, portfolioId, functionId);
    }

    @Operation(summary = "find all portfolio function", description = "포트폴리오의 모든 기능 명세 조회")
    @GetMapping("/{portfolioId}/function")
    public ResponseResult<List<PortfolioFunctionResponse>> findAllPortfolioFunction(Long userId, @PathVariable Long portfolioId) {
        List<PortfolioFunctionResponse> result = functionService.findPortfolioFunctions(userId, portfolioId);
        return new ResponseResult<>(result);
    }

}
