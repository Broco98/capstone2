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
import start.capstone2.domain.user.repository.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PortfolioApiAiService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final ChatClient chatClient;

//    @Async
    @Transactional
    public void generatePortfolioApi(Long userId, Long portfolioId, Long functionId) {

        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        if (!portfolio.getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가");
        }

        PortfolioFunction function = portfolio.getFunctions().stream()
                .filter(f -> f.getId().equals(functionId))
                .findAny().orElseThrow(() -> new NoSuchElementException("function이 존재하지 않습니다."));

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
                PortfolioApi api = PortfolioApi.builder()
                        .portfolio(portfolio)
                        .name(function.getName())
                        .build();

                for (Map<String, Object> data : dataList) {
                    Method method = switch ((String) data.get("method")) {
                        case "POST" -> Method.POST;
                        case "GET" -> Method.GET;
                        case "DELETE" -> Method.DELETE;
                        case "PUT" -> Method.PUT;
                        default -> null;
                    };
                    PortfolioApiModule module = PortfolioApiModule.builder()
                                .api(api)
                                .method(method)
                                .url((String) data.get("url"))
                                .response((String) data.get("response"))
                                .description((String) data.get("description"))
                                .build();
                        api.addModule(module);
                    }
                portfolio.addApi(api);
            }
        } catch (IOException e) {
            throw new IllegalStateException("jsonResult error", e);
        }
    }

    private String createMessage(PortfolioFunction function) {
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

        You need to generate a specification for RESTful APIs based on the project's features as input. For example, when you receive input like this:
        ---
        유저기능
        유저 생성: 새로운 유저를 생성합니다. 유저명, 이메일, 비밀번호 등의 정보를 입력받아 데이터베이스에 저장합니다.
        유저 삭제: 기존 유저를 삭제합니다. 유저의 고유 ID를 기준으로 데이터를 삭제합니다.
        ---
        You should provide a JSON response like this:

        {
             "data" : [
                 {
                     "method" : "(one of POST, GET, DELETE, PUT)",
                     "url": "(RESTful URL)",
                     "response": "(JSON formatted String indicating what should be returned)",
                     "description" : "(Description of the RESTful API specification in Korean)"
                 },
                 ... (Repeat as needed for the specifications you deem necessary)
             ]
         }
        
        The JSON response should meet the following conditions:
        1. All data should be within the data field.
        2. Inside data, there should be multiple sets of RESTful API specifications (method, url, response, description).
        3. Access to the method, description, and other fields should be possible as data[0].method, data[0].description, data[1].method, and so on.
        4. Include as many pairs of RESTful API specifications (method, url, response, description) as you deem necessary.
        This information will guide the actual design and coding process, providing detailed and precise instructions.
        """);
        Message userMessage = new SystemMessage(message);

        prompts.add(prompt);
        prompts.add(userMessage);
        return new Prompt(prompts);
    }
}
