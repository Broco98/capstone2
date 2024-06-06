package start.capstone2.service.portfolio.ai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioFunction;
import start.capstone2.domain.portfolio.PortfolioFunctionModule;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.dto.portfolio.PortfolioFunctionModuleRequest;
import start.capstone2.dto.portfolio.PortfolioFunctionRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PortfolioFunctionAiService {

    private final PortfolioRepository portfolioRepository;
    private final ChatClient chatClient;

//    @Async
    @Transactional
    public void generatePortfolioFunction(Long userId, Long portfolioId) {

        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        if (!portfolio.getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가");
        }

        Prompt prompt = createPrompt(portfolio.getDescription());
        String json = chatClient.call(prompt).getResult().getOutput().getContent();

        // parsing
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // JSON 데이터를 Map으로 파싱
            Map<String, List<Map<String, Object>>> map = objectMapper.readValue(json, new TypeReference<Map<String, List<Map<String, Object>>>>() {});

            // "data" 키를 통해 데이터 목록에 접근
            List<Map<String, Object>> dataList = map.get("data");
            if (dataList != null) {
                for (Map<String, Object> data : dataList) {
                    String moduleName = (String) data.get("module");

                    PortfolioFunction function = PortfolioFunction.builder()
                            .portfolio(portfolio)
                            .name(moduleName)
                            .build();

                    List<Map<String, String>> contentList = (List<Map<String, String>>) data.get("content");
                    for (Map<String, String> content : contentList) {
                        PortfolioFunctionModule module = PortfolioFunctionModule.builder()
                                .function(function)
                                .name(content.get("name"))
                                .description(content.get("description"))
                                .build();
                        function.addModule(module);
                    }
                    portfolio.addFunction(function);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("jsonResult error", e);
        }
    }

    private Prompt createPrompt(String message) {
        List<Message> prompts = new ArrayList<>();
        Message prompt = new SystemMessage("""
        
        When provided with a brief project description, you are required to generate a functional specification for the project. For example, if the input is 'Simple Bulletin Board Project', you should list the necessary functionalities for a bulletin board project, categorized by each feature, and provide detailed descriptions for each functionality.
        ---
        유저 기능 : 유저 생성, 유저 삭제, 유저 수정, 유저 조회
        게시판 기능 : 게시글 작성, 게시글 조회, 게시글 삭제, 게시글 스크랩, 게시글 좋아요, 게시글 수정
        ---
        This is just an example. When you provide the input, you should offer more detailed descriptions for each functionality.
        The response should be in JSON format, divided into functionalities so that it can be parsed accordingly:
        {
            "data": [
                {
                    "module": "유저 기능",
                    "content": [
                        {
                            "name": "유저 생성",
                            "description": "새로운 유저를 생성합니다. 유저명, 이메일, 비밀번호 등의 정보를 입력받아 데이터베이스에 저장합니다."
                        },
                        {
                            "name": "유저 삭제",
                            "description": "기존 유저를 삭제합니다. 유저의 고유 ID를 기준으로 데이터를 삭제합니다."
                        }
                    ]
                },
                {
                    "module": "게시판 기능",
                    "content": [
                        {
                            "name": "게시글 작성",
                            "description": "유저가 새로운 게시글을 작성할 수 있습니다. 제목, 내용, 이미지 등을 입력 받아 저장합니다."
                        }
                    ]
                }
                (Repeat as needed, without duplication)
            ]
        }
        
        module, name, description in 'Korean' Please
        
        The response must strictly follow the JSON format with multiple pairs of (name, content) within the data field.
        Inside content, there should be multiple pairs of (name, description). Ensure that the JSON format is correct to prevent errors during parsing using Java's objectMapper.
        This detailed specification will facilitate the actual design process, providing comprehensive guidance.
        """);
        Message userMessage = new SystemMessage(message);

        prompts.add(prompt);
        prompts.add(userMessage);
        return new Prompt(prompts);
    }

}
