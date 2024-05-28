package start.capstone2.controller.portfolio;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioApiRequest;
import start.capstone2.dto.portfolio.PortfolioApiResponse;
import start.capstone2.service.portfolio.PortfolioApiService;


import java.util.List;

@Tag(name="PortfolioApi api", description = "portfolio api명세 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioApiController {

    private final PortfolioApiService apiService;

    @Operation(summary = "create portfolio_api", description = "portfolio api 명세 하나를 생성합니다.")
    @PostMapping("/{portfolioId}/api")
    public Long createPortfolioApi(Long userId, @PathVariable Long portfolioId, PortfolioApiRequest request) {
        return apiService.createPortfolioApi(userId, portfolioId, request);
    }

    @Operation(summary = "update portfolio_api", description = "portfolio api 명세 하나를 수정합니다.")
    @PutMapping("/{portfolioId}/api/{apiId}")
    public void updatePortfolioApi(Long userId, @PathVariable Long portfolioId, @PathVariable Long apiId, PortfolioApiRequest request) {
        apiService.updatePortfolioApi(userId, apiId, request);
    }

    @Operation(summary = "delete portfolio_api", description = "portfolio api 명세 하나를 삭제합니다.")
    @DeleteMapping("/{portfolioId}/api/{apiId}")
    public void deletePortfolioApi(Long userId, @PathVariable Long portfolioId, @PathVariable Long apiId) {
        apiService.deletePortfolioApi(userId, portfolioId, apiId);
    }

    @Operation(summary = "find all portfolio_api", description = "portfolio에 있는 모든 portfolio_api를 조회합니다.")
    @GetMapping("/{portfolioId}/api")
    public ResponseResult<List<PortfolioApiResponse>> findAllPortfolioApi(Long userId, @PathVariable Long portfolioId) {
        List<PortfolioApiResponse> result = apiService.findPortfolioApis(userId, portfolioId);
        return new ResponseResult<>(result);
    }

}
