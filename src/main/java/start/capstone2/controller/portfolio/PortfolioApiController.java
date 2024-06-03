package start.capstone2.controller.portfolio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioApiRequest;
import start.capstone2.dto.portfolio.PortfolioApiResponse;
import start.capstone2.service.portfolio.PortfolioApiService;

import java.util.List;

@Tag(name="Portfolio Api Api", description = "portfolio api 명세 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioApiController {

    private final PortfolioApiService apiService;

    @Operation(summary = "create portfolio api")
    @PostMapping("/{portfolioId}/api")
    public Long createPortfolioApi(Long userId, @PathVariable Long portfolioId, PortfolioApiRequest request) {
        return apiService.createPortfolioApi(userId, portfolioId, request);
    }

    @Operation(summary = "update portfolio api")
    @PutMapping("/{portfolioId}/api/{apiId}")
    public void updatePortfolioApi(Long userId, @PathVariable Long portfolioId, @PathVariable Long apiId, PortfolioApiRequest request) {
        apiService.updatePortfolioApi(userId, portfolioId, apiId, request);
    }

    @Operation(summary = "delete portfolio api")
    @DeleteMapping("/{portfolioId}/api/{apiId}")
    public void deletePortfolioApi(Long userId, @PathVariable Long portfolioId, @PathVariable Long apiId) {
        apiService.deletePortfolioApi(userId, portfolioId, apiId);
    }

    @Operation(summary = "find all portfolio api", description = "portfolio에 있는 모든 portfolio api를 조회합니다.")
    @GetMapping("/{portfolioId}/api")
    public ResponseResult<List<PortfolioApiResponse>> findAllPortfolioApi(Long userId, @PathVariable Long portfolioId) {
        List<PortfolioApiResponse> result = apiService.findPortfolioApis(userId, portfolioId);
        return new ResponseResult<>(result);
    }
}
