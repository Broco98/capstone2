package start.capstone2.controller.ai;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import start.capstone2.service.portfolio.ai.PortfolioApiAiService;
import start.capstone2.service.portfolio.ai.PortfolioDatabaseAiService;

@Slf4j
@Tag(name = "Portfolio API 명세 자동 생성 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioApiAiController {

    private final PortfolioApiAiService apiAiService;

    @Operation(summary = "generate portfolio api", description = "function 하나를 선택해서 포트폴리오 api 명세를 자동으로 생성합니다.")
    @PostMapping("/{portfolioId}/function/{functionId}/api-generation")
    public ResponseEntity<String> generatePortfolioApi(@PathVariable Long portfolioId, @PathVariable Long functionId, HttpServletRequest servletRequest) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        apiAiService.generatePortfolioApi(userId, portfolioId, functionId);
        return ResponseEntity.ok("create success");
    }
}
