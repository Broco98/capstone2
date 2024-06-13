package start.capstone2.service.portfolio.ai;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import org.mockito.Mock;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import start.capstone2.domain.Image.Image;
import start.capstone2.domain.Image.S3Store;
import start.capstone2.domain.portfolio.*;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PortfolioDesignAiService {

    private final PortfolioRepository portfolioRepository;
    private final ChatClient chatClient;
    private final S3Store store;

//    @Async
    @Transactional
    public void generatePortfolioDesign(Long userId, Long portfolioId, Long functionId) {

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
                PortfolioDesign design = PortfolioDesign.builder()
                        .portfolio(portfolio)
                        .name(function.getName())
                        .build();

                for (Map<String, Object> data : dataList) {
                    Image image = store.saveImage(createDiagramImage((String) data.get("diagram")));
                    PortfolioDesignDiagram diagram = PortfolioDesignDiagram.builder()
                            .design(design)
                            .image(image)
                            .diagram((String) data.get("diagram"))
                            .description((String) data.get("description"))
                            .build();
                    design.addDiagram(diagram);
                }
                portfolio.addDesign(design);
                }
        } catch (IOException e) {
            throw new IllegalStateException("jsonResult error", e);
        }
    }

    private MultipartFile createDiagramImage(String diagram) {

        SourceStringReader reader = new SourceStringReader(diagram);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            reader.outputImage(os, new FileFormatOption(FileFormat.PNG));
            return new MockMultipartFile("file", "diagram_generated.png", "image/png", os.toByteArray());
        } catch (IOException e) {
          throw new RuntimeException("diagram image 생성 실패");
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

        You need to generate a diagram for a project based on the project's features as input. For example, when you receive input like this:
        ---
        유저기능
        유저 생성: 새로운 유저를 생성합니다. 유저명, 이메일, 비밀번호 등의 정보를 입력받아 데이터베이스에 저장합니다.
        유저 삭제: 기존 유저를 삭제합니다. 유저의 고유 ID를 기준으로 데이터를 삭제합니다.
        ---
        You should provide a JSON response like this:

        {
             "data" : [
                 {
                     "diagram" : "(sequence diagram using PlantUML, String type, in English)",
                     "description" : "('Description' of the diagram in 'Korean')"
                 },
                 (... Repeat as needed, without duplication)
                 {
                     "diagram" : "(use-case diagram using PlantUML, String type, in English)",
                     "description" : "('Description' of the diagram in 'Korean')"
                 },
                 (... Repeat as needed, without duplication)
                 {
                     "diagram" : "(class diagram using PlantUML, including variables and methods, String type, in English)",
                     "description" : "('Description' of the diagram in 'Korean')"
                 },
                 (... Repeat as needed, without duplication)
                 {
                     "diagram" : "(ER diagram using PlantUML, String type, in English)",
                     "description" : "('Description' of the diagram in 'Korean')"
                 },
                 (... Repeat as needed, without duplication)
             ]
         }

        The JSON response should meet the following conditions:

        1. All data should be within the data field.
        2. Inside data, there should be multiple sets of diagram information (diagram and description).
        3. Access to the diagram and description should be possible as data[0].diagram, data[0].description, data[1].diagram, and so on.
        4. Accessing data[0].diagram should provide the diagram information (PlantUML).
        5. Include as many pairs of diagram information (diagram, description) as you deem necessary.
        6. Provide a complete JSON format so that it can be parsed using objectMapper.
        This information will guide the actual design and coding process, providing detailed and precise instructions.
        """);
        Message userMessage = new SystemMessage(message);

        prompts.add(prompt);
        prompts.add(userMessage);
        return new Prompt(prompts);
    }
}
