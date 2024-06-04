package start.capstone2.service.portfolio.ai;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
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

@Service
@RequiredArgsConstructor
public class PortfolioCodeAiService {

    private final PortfolioRepository portfolioRepository;
    private final ChatClient chatClient;

    @Async
    @Transactional
    public void generatePortfolioCode(Long userId, Long portfolioId) {

        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();


        String message = createMessage(function);
        Prompt prompt = createPrompt(message);
        String json = chatClient.call(prompt).getResult().getOutput().getContent();

        // parsing
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // JSON 데이터를 Map으로 파싱
            Map<String, List<Map<String, Object>>> map = objectMapper.readValue(json, new TypeReference<Map<String, List<Map<String, Object>>>>() {});

            // "data" 키를 통해 데이터 목록에 접근
            List<Map<String, Object>> dataList = map.get("data");
            if (dataList != null) {
                PortfolioDatabase database = PortfolioDatabase.builder()
                        .portfolio(portfolio)
                        .name(function.getName())
                        .build();

                for (Map<String, Object> data : dataList) {
                        PortfolioDatabaseSchema schema = PortfolioDatabaseSchema.builder()
                                .database(database)
                                .schema((String) data.get("schema"))
                                .description((String) data.get("description"))
                                .build();
                        database.addSchema(schema);
                    }
                portfolio.addDatabase(database);
                }
        } catch (IOException e) {
            throw new IllegalStateException("jsonResult error", e);
        }
    }

    private String createMessage(Portfolio portfolio) {
        // 기능 명세서

        // 다이어그램
        
        // DB 스키마
        
        // API 명세

        List<PortfolioFunctionModule> modules = function.getModules();

        if (modules.isEmpty()) {
            throw new NoSuchElementException("function module이 존재하지 않습니다");
        }

        StringBuilder builder = new StringBuilder();
        builder.append(function.getName()).append("\n");
        for (PortfolioFunctionModule m : modules) {
            builder.append(m.getName()).append(": ").append(m.getDescription()).append("\n");
        }

        return builder.toString();
    }

    private Prompt createPrompt(String message) {
        List<Message> prompts = new ArrayList<>();
        Message prompt = new SystemMessage("""

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
                    "description" : (어떤 Table인지 설명, 한글로 설명)
                },
                {
                    "schema" : (Table 생성 SQL문, 필요한 속성을 포함해야 하고, 그 속성이 어떤 속성인지 주석으로 설명해야함)
                    "description" : (어떤 Table인지 설명, 한글로 설명)
                },
                ... (너가 필요한 Table 만큼 반복, 1개도 상관 없음)
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
        Message userMessage = new SystemMessage(message);

        prompts.add(prompt);
        prompts.add(userMessage);
        return new Prompt(prompts);
    }
}
