package start.capstone2.controller.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioApiRequest;
import start.capstone2.dto.portfolio.PortfolioApiResponse;
import start.capstone2.service.portfolio.PortfolioApiService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioApiController {

    private final PortfolioApiService apiService;

    @PostMapping("/{portfolioId}/api")
    public Long createPortfolioApi(Long userId, @PathVariable Long portfolioId, PortfolioApiRequest request) {
        return apiService.createPortfolioApi(userId, portfolioId, request);
    }

    @PutMapping("/{portfolioId}/api/{apiId}")
    public void updatePortfolioApi(Long userId, @PathVariable Long portfolioId, @PathVariable Long apiId, PortfolioApiRequest request) {
        apiService.updatePortfolioApi(userId, portfolioId, apiId, request);
    }

    @DeleteMapping("/{portfolioId}/api/{apiId}")
    public void deletePortfolioApi(Long userId, @PathVariable Long portfolioId, @PathVariable Long apiId) {
        apiService.deletePortfolioApi(userId, portfolioId, apiId);
    }

    @GetMapping("/{portfolioId}/api")
    public ResponseResult<List<PortfolioApiResponse>> findAllPortfolioApi(Long userId, @PathVariable Long portfolioId) {
        List<PortfolioApiResponse> result = apiService.findPortfolioApis(userId, portfolioId);
        return new ResponseResult<>(result);
    }

}
