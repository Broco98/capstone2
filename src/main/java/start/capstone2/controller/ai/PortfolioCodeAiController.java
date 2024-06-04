package start.capstone2.controller.ai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioDatabase;
import start.capstone2.domain.portfolio.PortfolioFunction;
import start.capstone2.domain.portfolio.PortfolioFunctionModule;
import start.capstone2.dto.portfolio.PortfolioDatabaseRequest;
import start.capstone2.dto.portfolio.PortfolioFunctionModuleRequest;
import start.capstone2.dto.portfolio.PortfolioFunctionRequest;
import start.capstone2.service.portfolio.PortfolioDatabaseService;
import start.capstone2.service.portfolio.PortfolioFunctionModuleService;
import start.capstone2.service.portfolio.PortfolioFunctionService;
import start.capstone2.service.portfolio.PortfolioService;
import start.capstone2.service.portfolio.ai.PortfolioDatabaseAiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Tag(name = "Portfolio Code 자동 생성 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioCodeAiController {

    private final PortfolioDatabaseAiService databaseAiService;

    @Operation(summary = "generate portfolio code", description = "function 하나를 선택해서 포트폴리오 code를 자동으로 생성합니다.")
    @PostMapping("/{portfolioId}/function/{functionId}/code-generation")
    public ResponseEntity<String> generatePortfolioCode(Long userId, @PathVariable Long portfolioId, @PathVariable Long functionId, @PathVariable Long databaseId) {
        databaseAiService.generatePortfolioDatabase(userId, portfolioId, functionId);
        return ResponseEntity.ok("생성 중 ... ");
    }
}
