package start.capstone2.controller.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioDesignRequest;
import start.capstone2.dto.portfolio.PortfolioDetailRequest;
import start.capstone2.dto.portfolio.PortfolioDetailResponse;
import start.capstone2.dto.portfolio.PortfolioFunctionRequest;
import start.capstone2.service.portfolio.PortfolioDesignService;
import start.capstone2.service.portfolio.PortfolioDetailService;
import start.capstone2.service.portfolio.PortfolioFunctionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioDetailController {

    private final PortfolioDetailService detailService;
    private final PortfolioFunctionService functionService;

//    @PostMapping("/{portfolioId}/detail")
//    public Long createPortfolioDetail(Long userId, @PathVariable Long portfolioId, PortfolioDetailRequest request) {
//        return detailService.createPortfolioDetail(userId, portfolioId, request);
//    }

    @PutMapping("/{portfolioId}/detail")
    public void updatePortfolioDetail(Long userId, @PathVariable Long portfolioId, PortfolioDetailRequest request) {
        detailService.updatePortfolioDetail(userId, portfolioId, request);
    }

    @GetMapping("/{portfolioId}/detail")
    public ResponseResult<PortfolioDetailResponse> findPortfolioDetail(Long userId, @PathVariable Long portfolioId) {
        return new ResponseResult<>(detailService.findPortfolioDetail(userId, portfolioId));
    }

    @PostMapping("/{portfolioId}/generate-functions")
    public void generatePortfolioFunction(Long userId, @PathVariable Long portfolioId) {
        List<PortfolioFunctionRequest> functionRequests= detailService.generatePortfolioFunctionRequest(userId, portfolioId);

        for (PortfolioFunctionRequest request : functionRequests) {
            functionService.createPortfolioFunction(userId, portfolioId, request);
        }
    }
}
