//package start.capstone2.controller.portfolio;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import start.capstone2.dto.portfolio.PortfolioTechStackRequest;
//import start.capstone2.service.portfolio.PortfolioTechStackService;
//
//@Tag(name = "Portfolio Tech Stack Api", description = "portfolio의 사용기술/환경 정보 api")
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/portfolio")
//public class PortfolioTechStackController {
//
//    private final PortfolioTechStackService techStackService;
//
//    @Operation(summary = "create portfolio tech stack", description = "여러개의 tech stack 생성")
//    @PostMapping("/{portfolioId}/tech-stack")
//    public ResponseEntity<String> createPortfolioTechStack(Long userId, @PathVariable Long portfolioId, PortfolioTechStackRequest request) {
//        techStackService.createPortfolioTechStack(userId, portfolioId, request);
//        return ResponseEntity.ok("tech-stack 생성 완료");
//    }
//
////    @Operation(summary = "find all portfolio tech-stack", description = "해당 portfolio의 모든 tech-stack 조회")
////    @GetMapping("/{portfolioId}/tech-stack")
////    public ResponseResult<List<PortfolioTechStackResponse>> findAllPortfolioTechStack(Long userId, @PathVariable Long portfolioId) {
////        return new ResponseResult<>(techStackService.findAllPortfolioTechStack(userId, portfolioId));
////    }
//
//    @Operation(summary = "delete portfolio tech stack", description = "해당 portfolio에서 선택한 techstack 단일 삭제")
//    @DeleteMapping("/{portfolioId}/tech-stack/{techStackId}")
//    public void deletePortfolioTechStack(Long userId, @PathVariable Long portfolioId, @PathVariable Long techStackId) {
//        techStackService.deletePortfolioTechStack(userId, portfolioId, techStackId);
//    }
//}
