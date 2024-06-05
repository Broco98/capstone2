package start.capstone2.controller.portfolio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
    public Long createPortfolioFunctionModule(HttpServletRequest servletRequest, @PathVariable Long functionId, PortfolioFunctionModuleRequest request) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        return moduleService.createPortfolioFunctionModule(userId, functionId, request);
    }

    @Operation(summary = "update portfolio function module")
    @PutMapping("{functionId}/module/{moduleId}")
    public void updatePortfolioFunctionModule(HttpServletRequest servletRequest, @PathVariable Long functionId, @PathVariable Long moduleId, PortfolioFunctionModuleRequest request) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        moduleService.updatePortfolioFunctionModule(userId, functionId, moduleId, request);
    }

    @Operation(summary = "delete portfolio function module")
    @DeleteMapping("{functionId}/module/{moduleId}")
    public void deletePortfolioFunctionModule(HttpServletRequest servletRequest, @PathVariable Long functionId, @PathVariable Long moduleId) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        moduleService.deletePortfolioFunctionModule(userId, functionId, moduleId);
    }


}
