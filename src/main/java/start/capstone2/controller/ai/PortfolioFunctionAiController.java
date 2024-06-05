package start.capstone2.controller.ai;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import start.capstone2.service.portfolio.ai.PortfolioFunctionAiService;

@Tag(name = "Portfolio Function 자동 생성 Api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio/")
public class PortfolioFunctionAiController {

    private final PortfolioFunctionAiService functionAiService;

    @PostMapping("/{portfolioId}/function-generation")
    public ResponseEntity<String> generatePortfolioFunction(@PathVariable Long portfolioId, HttpServletRequest servletRequest) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        functionAiService.generatePortfolioFunction(userId, portfolioId);
        return ResponseEntity.ok("생성 중 ... ");
    }

}
