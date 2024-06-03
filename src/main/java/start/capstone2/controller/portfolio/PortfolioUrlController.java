//package start.capstone2.controller.portfolio;
//
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//import start.capstone2.dto.ResponseResult;
//import start.capstone2.dto.portfolio.PortfolioUrlRequest;
//import start.capstone2.dto.portfolio.PortfolioUrlResponse;
//import start.capstone2.service.portfolio.PortfolioUrlService;
//
//import java.util.List;
//
//// TODO
//@Tag(name = "Portfolio Url Api", description = "portfolio 공유 url api")
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/portfolio")
//public class PortfolioUrlController {
//
//    private final PortfolioUrlService urlService;
//
//    @Operation(summary = "create portfolio url", description = "프로젝트를 선택한 url에 공유")
//    @PostMapping("/{portfolioId}/url")
//    public Long createPortfolioUrl(Long userId, @PathVariable Long portfolioId, PortfolioUrlRequest request) {
//        return urlService.createPortfolioUrl(userId, portfolioId, request.getUrlId());
//    }
//
//    @Operation(summary = "find all portfolio url", description = "해당 portfolio가 공유된 url 조회")
//    @GetMapping("/{portfolioId}/url")
//    public ResponseResult<List<PortfolioUrlResponse>> findAllPortfolioUrl(Long userId, @PathVariable Long portfolioId) {
//        return new ResponseResult<>(urlService.findAllByPortfolioId(userId, portfolioId));
//    }
//
//    @Operation(summary = "delete portfolio url", description = "선택한 프로젝트 url 공유 취소(삭제)")
//    @DeleteMapping("/{portfolioId}/url/{portfolioUrlId}")
//    public void deletePortfolioUrl(Long userId, @PathVariable Long portfolioId, @PathVariable Long portfolioUrlId) {
//        urlService.deletePortfolioUrl(userId, portfolioId, portfolioUrlId);
//    }
//
//}
