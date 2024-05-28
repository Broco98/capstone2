package start.capstone2.controller.portfolio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioFunctionModuleRequest;
import start.capstone2.dto.portfolio.PortfolioFunctionModuleResponse;
import start.capstone2.dto.portfolio.PortfolioFunctionRequest;
import start.capstone2.dto.portfolio.PortfolioFunctionResponse;
import start.capstone2.service.portfolio.PortfolioFunctionModuleService;
import start.capstone2.service.portfolio.PortfolioFunctionService;

import java.util.List;

@Tag(name = "PortfolioFunctionModule api", description = "포트폴리오 기능-모듈 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioFunctionModuleController {

    private final PortfolioFunctionService functionService;
    private final PortfolioFunctionModuleService moduleService;

    @Operation(summary = "create portfolio function module", description = "포트폴리오 기능 명세 모듈 생성")
    @PostMapping("/{portfolioId}/function/{functionId}/module")
    public Long createPortfolioFunctionModule(Long userId, @PathVariable Long portfolioId, @PathVariable Long functionId, PortfolioFunctionModuleRequest request) {
        return moduleService.createPortfolioFunctionModule(userId, functionId, request);
    }

    @Operation(summary = "update portfolio function module", description = "포트폴리오 기능 명세 모듈 수정")
    @PutMapping("/{portfolioId}/function/{functionId}/module/{moduleId}")
    public void updatePortfolioFunction(Long userId, @PathVariable Long portfolioId, @PathVariable Long functionId, @PathVariable Long moduleId, PortfolioFunctionModuleRequest request) {
        moduleService.updatePortfolioFunctionModule(userId, moduleId, request);
    }

    @Operation(summary = "delete portfolio function module", description = "포트폴리오 기능 명세 모듈 삭제")
    @DeleteMapping("/{portfolioId}/function/{functionId}/module/{moduleId}")
    public void deletePortfolioFunction(Long userId, @PathVariable Long portfolioId, @PathVariable Long functionId, @PathVariable Long moduleId) {
        functionService.deletePortfolioFunction(userId, functionId, moduleId);
    }

    @Operation(summary = "find all portfolio function module", description = "포트폴리오 기능의 모든 명세 모듈 조회")
    @GetMapping("/{portfolioId}/function/{functionId}/module")
    public ResponseResult<List<PortfolioFunctionModuleResponse>> findAllPortfolioFunctionModule(Long userId, @PathVariable Long portfolioId, @PathVariable Long functionId) {
        List<PortfolioFunctionModuleResponse> result = moduleService.findPortfolioFunctionModule(userId, functionId);
        return new ResponseResult<>(result);
    }
    
}
