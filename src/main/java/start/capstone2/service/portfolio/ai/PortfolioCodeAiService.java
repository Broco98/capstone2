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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.portfolio.*;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PortfolioCodeAiService {

    private final PortfolioRepository portfolioRepository;
    private final ChatClient chatClient;

    @Async
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

            message = createCodeGenerationMessage(portfolio, message);

            builder
                    .append("name: ").append(code.getName()).append(", ")
                    .append("description: ").append(code.getDescription()).append("\n");

            prompt = createCodeGenerationPrompt(message, builder.toString());
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
        참고로 너가 이미 생성해준 코드가 있다면, code 부분에 작성해 줄게 그것도 참고해 주면 좋을 것 같아.
        
        참고로, 프로젝트 다이어그램이나 프로젝트 DB스키마에서 클래스나 Table 들이 중복된 경우가 있을거야.
        그 이유는 기능별로 다이어그램과 DB스키마(Table)을 생성해서 그래. 예를 들면, 유저기능에서 유저 테이블과 클래스가 나오고,
        채팅 기능에서 유저 테이블과 클래스가 또 나와서 그런거야.
        이 점을 고려해서 중복된 테이블이 입력돼도 그중 모든 기능명세를 만족할 수 있는 하나의 Table과 클래스를 사용해줘.
        중복된 Table을 사용하면 안돼!.
        
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
            builder.append("===프로젝트 코드 구조===").append("\n");
            for (PortfolioCode code : portfolio.getCodes()) {
                builder.append("file name: ").append(code.getName()).append(", ");
                if (code.getCode() != null) {
                    builder.append("생성된 code: ").append(code.getCode()).append(", ");
                }
                builder.append("description: ").append(code.getDescription()).append("\n");
            }
        }

        return builder.toString();
    }

    private String createCodeStructureMessage(Portfolio portfolio) {
        StringBuilder builder = new StringBuilder();

        if (!portfolio.getTechStacks().isEmpty()) {
            builder.append("프로젝트 tech-stack: ").append(portfolio.getTechStacks()).append("\n");
        }

        if (!portfolio.getFunctions().isEmpty()) {
            builder.append("===프로젝트 기능 명세===").append("\n");
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
            builder.append("===프로젝트 API 명세===").append("\n");
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
            builder.append("===프로젝트 다이어그램===").append("\n");
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
            builder.append("===프로젝트 DB 스키마===").append("\n");
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

        너는 프로젝트의 정보를 입력으로 받으면 그 프로젝트 코드 구조(파일)를 생성해 줘야해
        프로젝트의 tech-stack을 참고해서 파일을 만들어줘 예를 들어서 java가 있으면 .java나 .class로 파일을 만들어야겠지?
        
        참고로, 프로젝트 다이어그램이나 프로젝트 DB스키마에서 클래스나 Table 들이 중복된 경우가 있을거야.
        그 이유는 기능별로 다이어그램과 DB스키마(Table)을 생성해서 그래. 예를 들면, 유저기능에서 유저 테이블과 클래스가 나오고,
        채팅 기능에서 유저 테이블과 클래스가 또 나와서 그런거야.
        이 점을 고려해서 중복된 테이블이 입력돼도 그중 모든 기능명세를 만족할 수 있는 하나의 Table과 클래스를 사용해줘.
        중복된 Table을 사용하면 안돼!.
        
        {
            "data" : [
                {
                    "name" : (필요한 파일 이름, 영어로)
                    "description" : (어떤 파일인지 설명, 한글로 설명, String형식)
                },
                {
                    "name" : (필요한 파일 이름, 영어로)
                    "description" : (어떤 파일인지 설명, 한글로 설명, String형식)
                },
                ... (너가 필요한 파일만큼 반복, 중복 X)
            ]
        }

        이런 json 형식의 답변을 기대하고 있어.
        json 응답은 다음과 같은 조건을 만족해야 돼

        1. 모든 데이터가 data안에 있고,
        2. 그 안에 여러개의 파일 정보(name, description)이 있는 형식이야
        3. data[0].name, data[0].description, data[1].name 이런 식으로 접근 가능하도록 하게 보내줘야 돼
        4. data[0].name 로 접근하면 file의 이름이 나와야 하는 거지.
        5. code의 구조 정보의 갯수 (name, description)의 쌍은 너가 필요하다고 생각되는 만큼만 작성해줘

        이 결과를 바탕으로 실제로 코딩을 진행할 수 있도록 최대한 자세하고 세밀하게 알려줘
        특히 이 결과는 코드를 생성하는데 사용하게 될 예정이야.
        """);
        Message userMessage = new SystemMessage(message);

        prompts.add(prompt);
        prompts.add(userMessage);
        return new Prompt(prompts);
    }
}
