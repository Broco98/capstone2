package start.capstone2.service.portfolio.ai;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.portfolio.*;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PortfolioCodeAiService {

    private final PortfolioRepository portfolioRepository;
    private final ChatClient chatClient;

//    @Async
    @Transactional
    public void generatePortfolioCode(Long userId, Long portfolioId) {

        log.info("start code generation");
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        if (!portfolio.getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가");
        }

        String message = createCodeStructureMessage(portfolio);
        Prompt prompt = createCodeStructurePrompt(message);
        String json = chatClient.call(prompt).getResult().getOutput().getContent();

        // create code structure
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // JSON 데이터를 Map으로 파싱
            Map<String, List<Map<String, Object>>> map = objectMapper.readValue(json, new TypeReference<Map<String, List<Map<String, Object>>>>() {});

            // "data" 키를 통해 데이터 목록에 접근
            List<Map<String, Object>> dataList = map.get("data");
            if (dataList != null) {
                for (Map<String, Object> data : dataList) {
                    PortfolioCode code = PortfolioCode.builder()
                            .portfolio(portfolio)
                            .name((String) data.get("name"))
                            .description((String) data.get("description"))
                            .build();

                    portfolio.addCode(code);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("jsonResult error", e);
        }

        // file 하나씩 생성
        for (PortfolioCode code : portfolio.getCodes()) {
            StringBuilder builder = new StringBuilder();
            builder
                    .append("name: ").append(code.getName()).append(", ")
                    .append("description: ").append(code.getDescription()).append("\n");

            prompt = createCodeGenerationPrompt(createCodeGenerationMessage(portfolio, message), builder.toString());
            json = chatClient.call(prompt).getResult().getOutput().getContent();

            // create code
            objectMapper = new ObjectMapper();
            try {
                // JSON 데이터를 Map으로 파싱
                Map<String, List<Map<String, Object>>> map = objectMapper.readValue(json, new TypeReference<Map<String, List<Map<String, Object>>>>() {});

                // "data" 키를 통해 데이터 목록에 접근
                List<Map<String, Object>> dataList = map.get("data");
                if (dataList != null) {
                    for (Map<String, Object> data : dataList) {
                        code.updatePortfolioCode((String) data.get("name"), (String) data.get("code"), (String) data.get("description"));
                    }
                }
            } catch (IOException e) {
                throw new IllegalStateException("jsonResult error", e);
            }
        }

    }


    private Prompt createCodeGenerationPrompt(String message, String userInput) {
        List<Message> prompts = new ArrayList<>();

        Message prompt = new SystemMessage("""
        너는 프로젝트의 정보를 입력으로 받으면 그 프로젝트 코드를 생성(작성)해 줘야해
        사용자가 입력한 file 의 코드만 생성하면 돼
        === 프로젝트 코드 구조 === 에 있는 파일 이름과 설명을 참고해서 통합성과 일관성을 고려해서 코드를 작성해줘
       
        {
            "data" : [
                {
                    "name" : (사용자가 입력한 프로젝트 코드 구조의 파일이름, 필요한 경우 수정해도 됨.)
                    "code" : (너가 생성해야할 부분, 코드 생성, 자세한 설명이 있는 한글 주석 포함, String 형식)
                    "description" : (사용자가 입력한 프로젝트 코드 구조의 파일설명, String 형식, 필요한 경우 수정해도 됨.)
                }
            ]
        }
        
        이런 json 형식의 답변을 기대하고 있어.
        json 응답은 다음과 같은 조건을 만족해야 돼
        
        1. 모든 데이터가 data 안에 있고,
        2. 그 안에 사용자가 입력한 파일 정보(name, code, description)가 있는 형식이야
        3. data[0].name, data[0].description 이런식으로 접근 가능해야 돼.
        4. data[0].name 로 접근하면 file 의 이름이 나와야 하는 거지.
        5. 사용자가 입력한 파일에 대해서만 코드를 생성해면 돼. 즉 하나의 json 응답을 기대하고 있는거지.
        6. 통합성과 일관성을 고려해줘.
        
        이 결과를 바탕으로 코드를 모아서 실제 프로젝트를 만들 예정이야. 그래서 통합성을 잘 고려해줘.
        """);
        Message info = new SystemMessage(message);
        Message userMessage = new UserMessage(userInput);

        prompts.add(prompt);
        prompts.add(info);
        prompts.add(userMessage);
        return new Prompt(prompts);
    }

    private String createCodeGenerationMessage(Portfolio portfolio, String message) {
        StringBuilder builder = new StringBuilder();

        if (message != null)
            builder.append(message);

        if (!portfolio.getCodes().isEmpty()) {
            builder.append("===Project code structure===").append("\n");
            for (PortfolioCode code : portfolio.getCodes()) {
                builder.append("file name: ").append(code.getName()).append(", ");
                builder.append("description: ").append(code.getDescription()).append("\n");
            }
        }

        return builder.toString();
    }

    private String createCodeStructureMessage(Portfolio portfolio) {
        StringBuilder builder = new StringBuilder();

        if (!portfolio.getTechStacks().isEmpty()) {
            builder.append("Project tech-stacks: ").append(portfolio.getTechStacks()).append("\n");
        }

        if (!portfolio.getFunctions().isEmpty()) {
            builder.append("===Project API Specification===").append("\n");
            for (PortfolioFunction function : portfolio.getFunctions()) {
                builder
                        .append(function.getName()).append("\n");
                for (PortfolioFunctionModule m : function.getModules()) {
                    builder.append(m.getName()).append(": ").append(m.getDescription()).append("\n");
                }
                builder.append("\n");
            }
        }

        if (!portfolio.getApis().isEmpty()) {
            builder.append("===Project RESTful APIs===").append("\n");
            for (PortfolioApi api : portfolio.getApis()) {
                builder.append(api.getName()).append("\n");
                for (PortfolioApiModule m : api.getModules()) {
                    builder
                            .append("method: ").append(m.getMethod()).append(", ")
                            .append("url: ").append(m.getUrl()).append(", ")
                            .append("response: ").append(m.getResponse()).append(", ")
                            .append("description: ").append(m.getDescription()).append("\n");
                }
                builder.append("\n");
            }
        }

        if (!portfolio.getDesigns().isEmpty()) {
            builder.append("===Project Diagram===").append("\n");
            for (PortfolioDesign design : portfolio.getDesigns()) {
                builder.append(design.getName()).append("\n");
                for (PortfolioDesignDiagram d : design.getDiagrams()) {
                    builder
                            .append("diagram: ").append(d.getDiagram()).append(", ")
                            .append("description: ").append(d.getDescription()).append("\n");
                }
                builder.append("\n");
            }
        }

        if (!portfolio.getDatabases().isEmpty()) {
            builder.append("===Project Database Schema===").append("\n");
            for (PortfolioDatabase database : portfolio.getDatabases()) {
                builder.append(database.getName()).append("\n");
                for (PortfolioDatabaseSchema s : database.getSchemas()) {
                    builder
                            .append("schema: ").append(s.getSchema()).append(", ")
                            .append("description: ").append(s.getDescription()).append("\n");
                }
                builder.append("\n");
            }
        }
        builder.append("=");

        return builder.toString();
    }

    private Prompt createCodeStructurePrompt(String message) {
        List<Message> prompts = new ArrayList<>();
        Message prompt = new SystemMessage("""
        너는 프로젝트의 정보를 입력으로 받으면 그 프로젝트 코드를 생성(작성)해 줘야해
        사용자가 입력한 file 의 코드만 생성하면 돼
        === 프로젝트 코드 구조 === 에 있는 파일 이름과 설명을 참고해서 통합성과 일관성을 고려해서 코드를 작성해줘
        
        {
            "data" : [
                {
                    "name" : (사용자가 입력한 프로젝트 코드 구조의 파일이름, 필요한 경우 수정해도 됨.)
                    "code" : (너가 생성해야할 부분, 코드 생성, 자세한 설명이 있는 한글 주석 포함, String 형식)
                    "description" : (사용자가 입력한 프로젝트 코드 구조의 파일설명, String 형식, 필요한 경우 수정해도 됨.)
                }
            ]
        }

        이런 json 형식의 답변을 기대하고 있어.
        json 응답은 다음과 같은 조건을 만족해야 돼

        1. 모든 데이터가 data 안에 있고,
        2. 그 안에 사용자가 입력한 파일 정보(name, code, description)가 있는 형식이야
        3. data[0].name, data[0].description 이런식으로 접근 가능해야 돼.
        4. data[0].name 로 접근하면 file 의 이름이 나와야 하는 거지.
        5. 사용자가 입력한 파일에 대해서만 코드를 생성해면 돼. 즉 하나의 json 응답을 기대하고 있는거지.
        6. 통합성과 일관성을 고려해줘.

        이 결과를 바탕으로 코드를 모아서 실제 프로젝트를 만들 예정이야. 그래서 통합성을 잘 고려해줘.
        """);
        Message userMessage = new SystemMessage(message);

        prompts.add(prompt);
        prompts.add(userMessage);
        return new Prompt(prompts);
    }
}
