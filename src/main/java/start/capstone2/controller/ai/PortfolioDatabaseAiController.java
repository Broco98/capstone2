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
        PortfolioFunction function = functionService.findById(functionId);


        // 프롬프트 셋팅
        List<Message> prompts = new ArrayList<>();
        Message prompt1 = new SystemMessage("""
        너는 프로젝트의 모듈을 입력으로 받으면 그 프로젝트 모듈에 해당하는 DB 스키마를 생성해 줘야돼.
        예를 들어서 입력으로
            
            유저기능
            유저 생성: 새로운 유저를 생성합니다. 유저명, 이메일, 비밀번호 등의 정보를 입력받아 데이터베이스에 저장합니다.
            유저 삭제: 기존 유저를 삭제합니다. 유저의 고유 ID를 기준으로 데이터를 삭제합니다.
        
        이라고 입력받으면 해당하는 유저 기능을 구현하기 위한 DB 스키마를 생성해 줘야돼.
        SQL 문과 그 설명 (주석)으로 생성해줘
        최대한 자세하게 생성하고 알려줘야돼
        
        답변은 json형식으로 해주고, 기능별로 파싱할 수 있도록 나눠서 답변해줘야해
        다음은 너가 보내줘야 할 json 양식의 예시야
        {
            "data": [{
                    "schema": (SQL문과 그 설명 주석들)
                    "description" : 스카마에 대한 설명 
                ],
                [{
                    "schema": (SQL문과 그 설명 주석들)
                    "description" : 스카마에 대한 설명
                }]
            } 
        }
        
        모든 데이터가 data안에 있고,
        그 안에 여러개의 db 정보(schema, description)이 있는 형식이야
        
        db정보의 갯수 (schema, description)의 쌍은 너가 필요하다고 생각되는 만큼만 작성해줘
       
        이 결과를 바탕으로 실제로 설계와 코딩을 진행할 수 있도록 최대한 자세하고 세밀하게 알려줘
        """);

        StringBuilder builder = new StringBuilder();
        builder.append(function.getName()).append("\n");
        List<PortfolioFunctionModule> modules = function.getModules();
        for (PortfolioFunctionModule module : modules) {
            builder.append(module.getName()).append(" ").append(module.getDescription()).append("\n");
        }
        Message userMessage = new SystemMessage(builder.toString());

        // 프롬프트 추가
        prompts.add(prompt1);
        prompts.add(userMessage);

        // 생성
        Prompt prompt = new Prompt(prompts);
        String jsonResult = chatClient.call(prompt).getResult().getOutput().getContent();
        log.info("jsonResult={}", jsonResult);

        // parsing
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            Map<String, Object> functionMap = objectMapper.readValue(jsonResult, new TypeReference<Map<String, Object>>() {});
//            List<Map<String, Object>> dataList = (List<Map<String, Object>>) functionMap.get("data");
//
//            if (dataList != null && !dataList.isEmpty()) {
//                for (int i = 0; i < dataList.size(); i++) {
//                    Map<String, Object> module = dataList.get(i);
//
//                    PortfolioFunction function = functionService.createPortfolioFunctionReturnObject(userId, portfolioId, new PortfolioFunctionRequest((String) module.get("module")));
//                    List<Map<String, String>> contents = (List<Map<String, String>>) module.get("content");
//
//                    for (Map<String, String> content : contents) {
//                        moduleService.createPortfolioFunctionModule(userId, function, new PortfolioFunctionModuleRequest(content.get("name"), content.get("description")));
//                    }
//
//                }
//            }
//
//        } catch (JsonProcessingException e) {
//            throw new IllegalStateException("jsonResult error", e);
//        }

        return ResponseEntity.ok("생성 완료!");
    }


}
