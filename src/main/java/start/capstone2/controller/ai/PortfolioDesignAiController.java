package start.capstone2.controller.ai;

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
import start.capstone2.domain.portfolio.PortfolioFunction;
import start.capstone2.domain.portfolio.PortfolioFunctionModule;
import start.capstone2.service.portfolio.PortfolioDesignService;
import start.capstone2.service.portfolio.PortfolioFunctionService;
import start.capstone2.service.portfolio.PortfolioService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Tag(name = "Portfolio Function 자동 생성 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ai/portfolio/")
public class PortfolioDesignAiController {

    private final PortfolioService portfolioService;
    private final PortfolioDesignService designService;
    private final PortfolioFunctionService functionService;
    private final ChatClient chatClient;

    @PostMapping("/{portfolioId}/design")
    public ResponseEntity<String> generatePortfolioDesign(Long userId, @PathVariable Long portfolioId) {

        Portfolio portfolio = portfolioService.findById(portfolioId);
        List<PortfolioFunction> functions = portfolio.getFunctions();

        // 프롬프트 셋팅
        List<Message> prompts = new ArrayList<>();
        Message prompt1 = new SystemMessage("""
        너는 프로젝트 기능을 입력으로 받으면 그 프로젝트의 UML 다이어그램을 만들어 줘야돼.
        예를 들어서 입력으로
        ---
        유저기능
        유저 생성
        유저 삭제
        ---
        이런 입력을 받으면
        
        {
            "data" : [
                {
                    "design" : (시퀸스 다이어그램 PlantUML 사용)
                    "description" : (다이어그램 설명)
                },
                {
                    "design" : (유스케이스 다이어그램 PlantUML 사용)
                    "description" : (다이어그램 설명)
                },
                {
                    "design" : (클래스 다이어그램 PlantUML 사용, 변수와 메서드 포함)
                    "description" : (다이어그램 설명)
                },
                {
                    "design" : (상태 다이어그램 PlantUML 사용)
                    "description" : (다이어그램 설명)
                },
                {
                    "design" : (유스케이스 다이어그램 PlantUML 사용)
                    "description" : (다이어그램 설명)
                },
            ]
        }
        
        이런 json 형식의 답변을 기대하고 있어.
        json 응답은 다음과 같은 조건을 만족해야 돼
          
        1. 모든 데이터가 data안에 있고,
        2. 그 안에 여러개의 db 정보(design, description)이 있는 형식이야
        3. data[0].design, data[0].description, data[1].schema 이런 식으로 접근 가능하도록 하게 보내줘야 돼
        4. data[0].design 로 접근하면 ER diagram 정보를 가져올 수 있어야 돼
        5. ER 다이어그램의 갯수 (schema, description)의 쌍은 너가 필요하다고 생각되는 만큼만 작성해줘
        
        이 결과를 바탕으로 실제로 설계와 코딩을 진행할 수 있도록 최대한 자세하고 세밀하게 알려줘
        """);

        StringBuilder builder = new StringBuilder();
        for (PortfolioFunction function : functions) {
            List<PortfolioFunctionModule> modules = function.getModules();
            builder.append(function.getName()).append("\n");
            for (PortfolioFunctionModule module : modules) {
                builder.append(module.getName()).append("\n");
            }
            builder.append("\n");
        }

        Message userMessage = new SystemMessage(builder.toString());

        // 프롬프트 추가
        prompts.add(prompt1);
        prompts.add(userMessage);

        // 생성
        Prompt prompt = new Prompt(prompts);
        String jsonResult = chatClient.call(prompt).getResult().getOutput().getContent();
        log.info("result={}", jsonResult);
        System.out.println(jsonResult);


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
