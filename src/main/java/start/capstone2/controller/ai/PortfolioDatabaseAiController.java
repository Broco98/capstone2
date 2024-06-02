package start.capstone2.controller.ai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Tag(name = "Portfolio Function 자동 생성 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ai/portfolio/")
public class PortfolioDatabaseAiController {

    private final PortfolioService portfolioService;
    private final PortfolioFunctionService functionService;
    private final PortfolioDatabaseService databaseService;
    private final ChatClient chatClient;

    @PostMapping("/{portfolioId}/function/{functionId}/database")
    public ResponseEntity<String> generatePortfolioDatabase(Long userId, @PathVariable Long portfolioId, @PathVariable Long functionId) {

        Portfolio portfolio = portfolioService.findById(portfolioId);
        PortfolioFunction inputFunction = functionService.findById(functionId);
        List<PortfolioFunction> functions = portfolio.getFunctions();

        StringBuilder builder = new StringBuilder();
        for (PortfolioFunction function : functions) {
            List<PortfolioFunctionModule> modules = function.getModules();
            builder.append(function.getName()).append("\n");
            for (PortfolioFunctionModule module : modules) {
                builder.append(module.getName()).append("\n");
            }
            builder.append("\n");
        }

        // 프롬프트 셋팅
        List<Message> prompts = new ArrayList<>();
        Message prompt1 = new SystemMessage("""
                
                너는 프로젝트 기능을 입력으로 받으면 그 프로젝트 기능에 해당하는 DB 테이블을 생성해 쥐야 돼.
                예를 들어서 입력으로
                ---
                유저기능
                유저 생성: 새로운 유저를 생성합니다. 유저명, 이메일, 비밀번호 등의 정보를 입력받아 데이터베이스에 저장합니다.
                유저 삭제: 기존 유저를 삭제합니다. 유저의 고유 ID를 기준으로 데이터를 삭제합니다.
                ---
                이런 입력을 받으면
                
                {
                    "data" : [
                        {
                            "schema" : (Table 생성 SQL문, 필요한 속성을 포함해야 하고, 그 속성이 어떤 속성인지 주석으로 설명해야함)
                            "descriptopn" : (어떤 Table인지 설명)
                        },
                        {
                            "schema" : (Table 생성 SQL문, 필요한 속성을 포함해야 하고, 그 속성이 어떤 속성인지 주석으로 설명해야함)
                            "descriptopn" : (어떤 Table인지 설명)
                        },
                        ... (너가 필요한 Table 만큼 반복)
                    ]
                
                }
                
                이런 json 형식의 답변을 기대하고 있어.
                json 응답은 다음과 같은 조건을 만족해야 돼
                  
                1. 모든 데이터가 data안에 있고,
                2. 그 안에 여러개의 db 정보(schema, description)이 있는 형식이야
                3. data[0].schema, data[0].description, data[1].schema 이런 식으로 접근 가능하도록 하게 보내줘야 돼
                4. data[0].schema 로 접근하면 Create Table 문이 나와야 하는 거지.
                5. db 정보의 갯수 (schema, description)의 쌍은 너가 필요하다고 생각되는 만큼만 작성해줘
                6. Create Table SQL문만 있으면 돼. INDEX나 function은 생성해줄 필요 없어
                
                이 결과를 바탕으로 실제로 설계와 코딩을 진행할 수 있도록 최대한 자세하고 세밀하게 알려줘
                """);

        builder = new StringBuilder();
        builder.append(inputFunction.getName()).append("\n");
        List<PortfolioFunctionModule> modules = inputFunction.getModules();
        for (PortfolioFunctionModule module : modules) {
            builder.append(module.getName()).append(": ").append(module.getDescription()).append("\n");
        }
        Message userMessage = new SystemMessage(builder.toString());

        // 프롬프트 추가
        prompts.add(prompt1);
        prompts.add(userMessage);

        // 생성
        Prompt prompt = new Prompt(prompts);
        String jsonResult = chatClient.call(prompt).getResult().getOutput().getContent();
        System.out.println(jsonResult);

        // parsing
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> map = objectMapper.readValue(jsonResult, new TypeReference<Map<String, Object>>() {});
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) map.get("data");

            if (dataList != null && !dataList.isEmpty()) {
                for (int i = 0; i < dataList.size(); i++) {
                    databaseService.createPortfolioDatabase(userId, portfolioId, new PortfolioDatabaseRequest((String)dataList.get(i).get("schema"), (String)dataList.get(i).get("description")));

                }
            }

        } catch (JsonProcessingException e) {
            throw new IllegalStateException("jsonResult error", e);
        }

        return ResponseEntity.ok("생성 완료!");
    }


}
