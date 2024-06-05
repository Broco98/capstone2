package start.capstone2.controller.portfolio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioFunctionModuleRequest;
import start.capstone2.dto.portfolio.PortfolioFunctionModuleResponse;
import start.capstone2.service.portfolio.PortfolioFunctionModuleService;
import start.capstone2.service.portfolio.PortfolioFunctionService;

import java.util.List;

@Tag(name = "Portfolio Function Module Api", description = "portfolio function의 모듈 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio-function")
public class PortfolioFunctionModuleController {

    private final PortfolioFunctionModuleService moduleService;

    @Operation(summary = "create portfolio function module")
    @PostMapping("{functionId}/module")
    public Long createPortfolioFunctionModule(Long userId, @PathVariable Long functionId, PortfolioFunctionModuleRequest request) {
        return moduleService.createPortfolioFunctionModule(userId, functionId, request);
    }

    @Operation(summary = "update portfolio function module")
    @PutMapping("{functionId}/module/{moduleId}")
    public void updatePortfolioFunctionModule(Long userId, @PathVariable Long functionId, @PathVariable Long moduleId, PortfolioFunctionModuleRequest request) {
        moduleService.updatePortfolioFunctionModule(userId, functionId, moduleId, request);
    }

    @Operation(summary = "delete portfolio function module")
    @DeleteMapping("{functionId}/module/{moduleId}")
    public void deletePortfolioFunctionModule(Long userId, @PathVariable Long functionId, @PathVariable Long moduleId) {
        moduleService.deletePortfolioFunctionModule(userId, functionId, moduleId);
    }

//    @Operation(summary = "find all portfolio function module", description = "포트폴리오 기능의 모든 명세 모듈 조회")
//    @GetMapping("{functionId}/module")
//    public ResponseResult<List<PortfolioFunctionModuleResponse>> findAllPortfolioFunctionModule(Long userId, @PathVariable Long functionId) {
//        List<PortfolioFunctionModuleResponse> result = moduleService.findPortfolioFunctionModule(userId, functionId);
//        return new ResponseResult<>(result);
//    }
    
}
