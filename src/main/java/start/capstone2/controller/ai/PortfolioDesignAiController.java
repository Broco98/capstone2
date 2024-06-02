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
        너는 간단한 프로젝트 설명을 입력받으면 그 프로젝트에 필요한 ER 다이어그램를 작성해야 돼.
        예를 들어서 입력으로 
        
        유저 기능
        유저 생성
        유저 삭제
        유저 수정
        유저 조회
        
        게시판 기능
        게시글 작성
        게시글 조회
        게시글 삭제
        게시글 스크랩
        게시글 좋아요
        게시글 수정
        
        이렇게 입력 받으면 먼저 필요한 Entity와 속성을 생각하고,
        그 후 연결관계를 생각해서 ER 다이어그램을 생성하는거야
        ER 다이어그램으로 코드를 작성할 수 있을 정도로 자세하게 작성해줘
        
        답변은 json형식으로 해주고, 기능별로 파싱할 수 있도록 나눠서 답변해줘야해
        
        다음은 너가 보내줘야 할 json 양식의 예시야
        {
            "data": {
                "design": (ER 다이어그램)
                "description": (설명)
            }
        }
        
        모든 데이터가 data안에 있고,
        ER 다이어그램 정보는 "design"안에, 그 설명은 "description"에 작성해줘
        
        ER 다이어그램을 바탕으로 설계를 진행하고 코드를 작성할 수 있도록 자세하게 작성해줘
        그리고 ER 다이어그램은 PlantUML 사용할 예정이니까 PlantUML을 사용할 수 있도록 작성해줘
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
