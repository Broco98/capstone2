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
import start.capstone2.service.portfolio.ai.PortfolioCodeAiService;

@Slf4j
@Tag(name = "Portfolio Code 자동 생성 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioCodeAiController {

    private final PortfolioCodeAiService codeAiService;

    @Operation(summary = "generate portfolio code", description = "function 하나를 선택해서 포트폴리오 code를 자동으로 생성합니다.")
    @PostMapping("/{portfolioId}/code-generation")
    public ResponseEntity<String> generatePortfolioCode(@PathVariable Long portfolioId, HttpServletRequest servletRequest) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        codeAiService.generatePortfolioCode(userId, portfolioId);
        return ResponseEntity.ok("create success");
    }
}
