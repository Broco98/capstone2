package start.capstone2.controller.ai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
import start.capstone2.domain.portfolio.PortfolioFunction;
import start.capstone2.dto.portfolio.PortfolioFunctionModuleRequest;
import start.capstone2.dto.portfolio.PortfolioFunctionRequest;
import start.capstone2.service.portfolio.PortfolioFunctionModuleService;
import start.capstone2.service.portfolio.PortfolioFunctionService;
import start.capstone2.service.portfolio.PortfolioService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Tag(name = "Portfolio Function 자동 생성 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ai/portfolio/")
public class PortfolioFunctionAiController {

    private final PortfolioService portfolioService;
    private final PortfolioFunctionService functionService;
    private final PortfolioFunctionModuleService moduleService;
    private final ChatClient chatClient;

    @PostMapping("/{portfolioId}/function")
    public ResponseEntity<String> generatePortfolioFunction(Long userId, @PathVariable Long portfolioId) {

        Portfolio portfolio = portfolioService.findById(portfolioId);

        // 프롬프트 셋팅
        List<Message> prompts = new ArrayList<>();
        Message prompt1 = new SystemMessage("""
        너는 간단한 프로젝트 설명을 입력받으면 그 프로젝트에 필요한 기능명세서를 작성해야 돼.
        예를 들어서 입력으로 '간단한 게시판 프로젝트' 라고 입력받으면 게시판 프로젝트에 필요한 기능들을 기능별로 작성하는거야
        
        유저 기능 : 유저 생성, 유저 삭제, 유저 수정, 유저 조회
        게시판 기능 : 게시글 작성, 게시글 조회, 게시글 삭제, 게시글 스크랩, 게시글 좋아요, 게시글 수정
        
        이건 예시고 너가 알려줄 땐 각 기능에 설명을 주가해서 더 자세하게 알려줘야 해
        답변은 json형식으로 해주고, 기능별로 파싱할 수 있도록 나눠서 답변해줘야해
        
        다음은 너가 보내줘야 할 json 양식의 예시야
        {
            "data": [{
                    "module": "유저기능",
                    "content": [
                        {
                            "name": "유저 생성",
                            "description": "새로운 유저를 생성합니다. 유저명, 이메일, 비밀번호 등의 정보를 입력받아 데이터베이스에 저장합니다."
                        },
                        {
                            "name": "유저 삭제",
                            "description": "기존 유저를 삭제합니다. 유저의 고유 ID를 기준으로 데이터를 삭제합니다."
                        }
                    ],
                ],
                [
                    "module": "게시판 기능",
                    "content": [
                        {
                            "name": "게시글 작성",
                            "description": "유저가 새로운 게시글을 작성할 수 있습니다. 제목, 내용, 이미지 등을 입력 받아 저장합니다."
                        }
                    ]
                ]
            } 
        }
        
        모든 데이터가 data안에 있고,
        그 안에 여러개의 module 이 있고,
        각각의 module 안에는 name과 description이 있어서
        유저기능은 data[0], 게시판 기능은 data[1] 이런 식으로 접근 가능하도록 하게 보내줘야 돼
        
        이 결과를 바탕으로 실제로 설계를 진행할 수 있도록 최대한 자세하고 세밀하게 알려줘
        """);
        Message userMessage = new SystemMessage(portfolio.getDescription());

        // 프롬프트 추가
        prompts.add(prompt1);
        prompts.add(userMessage);

        // 생성
        Prompt prompt = new Prompt(prompts);
        String jsonResult = chatClient.call(prompt).getResult().getOutput().getContent();

        // parsing
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> functionMap = objectMapper.readValue(jsonResult, new TypeReference<Map<String, Object>>() {});
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) functionMap.get("data");

            if (dataList != null && !dataList.isEmpty()) {
                for (int i = 0; i < dataList.size(); i++) {
                    Map<String, Object> module = dataList.get(i);

                    PortfolioFunction function = functionService.createPortfolioFunctionReturnObject(userId, portfolioId, new PortfolioFunctionRequest((String) module.get("module")));
                    List<Map<String, String>> contents = (List<Map<String, String>>) module.get("content");

                    for (Map<String, String> content : contents) {
                        moduleService.createPortfolioFunctionModule(userId, function, new PortfolioFunctionModuleRequest(content.get("name"), content.get("description")));
                    }

                }
            }

        } catch (JsonProcessingException e) {
            throw new IllegalStateException("jsonResult error", e);
        }

        return ResponseEntity.ok("생성 완료!");
    }


}
