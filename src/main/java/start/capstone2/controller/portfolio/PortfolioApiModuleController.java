package start.capstone2.controller.portfolio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioApiModuleRequest;
import start.capstone2.dto.portfolio.PortfolioApiRequest;
import start.capstone2.dto.portfolio.PortfolioApiResponse;
import start.capstone2.service.portfolio.PortfolioApiModuleService;
import start.capstone2.service.portfolio.PortfolioApiService;

import java.util.List;

@Tag(name="PortfolioApiModule api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio-api")
public class PortfolioApiModuleController {

    private final PortfolioApiModuleService apiModuleService;

    @Operation(summary = "create portfolio api module")
    @PostMapping("/{apiId}/module")
    public Long createPortfolioApiModule(@PathVariable Long apiId, PortfolioApiModuleRequest request, HttpServletRequest servletRequest) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        return apiModuleService.createPortfolioApiModule(userId, apiId, request);
    }

    @Operation(summary = "update portfolio api module")
    @PutMapping("/{apiId}/module/{moduleId}")
    public void updatePortfolioApi(@PathVariable Long apiId, @PathVariable Long moduleId, PortfolioApiModuleRequest request, HttpServletRequest servletRequest) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        apiModuleService.updatePortfolioApiModule(userId, apiId, moduleId, request);
    }

    @Operation(summary = "delete portfolio api module")
    @DeleteMapping("/{apiId}/module/{moduleId}")
    public void deletePortfolioApi(@PathVariable Long apiId, @PathVariable Long moduleId, HttpServletRequest servletRequest) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        apiModuleService.deletePortfolioApiModule(userId, apiId, moduleId);
    }

//    @Operation(summary = "find all portfolio_api_module", description = "portfolio에 있는 모든 portfolio_api를 조회합니다.")
//    @GetMapping("/{portfolioId}/api")
//    public ResponseResult<List<PortfolioApiResponse>> findAllPortfolioApi(Long userId, @PathVariable Long portfolioId) {
//        List<PortfolioApiResponse> result = apiService.findPortfolioApis(userId, portfolioId);
//        return new ResponseResult<>(result);
//    }

}
