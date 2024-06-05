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

    @Async
    @Transactional
    public void generatePortfolioDesign(Long userId, Long portfolioId, Long functionId) {

        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
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

        너는 프로젝트 기능을 입력으로 받으면 그 프로젝트의 diagram을 만들어 줘야돼.
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
                    "diagram" : (sequence diagram, PlantUML 사용, String 타입, 영어로 생성)
                    "description" : (다이어그램 설명, 한글로 설명)
                },
                (... 너가 필요한 만큼 반복, 중복 X)
                {
                    "diagram" : (use-case diagram, PlantUML 사용, String 타입, 영어로 생성)
                    "description" : (다이어그램 설명, 한글로 설명)
                },
                (... 너가 필요한 만큼 반복, 중복 X)
                {
                    "diagram" : (class diagram, PlantUML 사용, 변수와 메서드 포함, String 타입, 영어로 생성)
                    "description" : (다이어그램 설명, 한글로 설명)
                },
                (... 너가 필요한 만큼 반복, 중복 X)
                {
                    "diagram" : (ER diagram, PlantUML 사용, String 타입, 영어로 생성)
                    "description" : (다이어그램 설명, 한글로 설명)
                },
                (... 너가 필요한 만큼 반복, 중복 X)
            ]
        }

        이런 json 형식의 답변을 기대하고 있어.
        json 응답은 다음과 같은 조건을 만족해야 돼

        1. 모든 데이터가 data안에 있고,
        2. 그 안에 여러개의 diagram 정보(diagram, description)이 있는 형식이야
        3. data[0].diagram, data[0].description, data[1].diagram 이런 식으로 접근 가능하도록 하게 보내줘야 돼
        4. data[0].diagram 로 접근하면 다이어그램 정보(PlantUML)를 가져올 수 있어야 돼
        5. diagram의 갯수 (diagram, description)의 쌍은 너가 필요하다고 생각되는 만큼만 작성해줘
        6. objectMapper로 parsing할 수 있도록 완전한 json 형식으로 보내줘야 해

        이 결과를 바탕으로 실제로 설계와 코딩을 진행할 수 있도록 최대한 자세하고 세밀하게 알려줘
        """);
        Message userMessage = new SystemMessage(message);

        prompts.add(prompt);
        prompts.add(userMessage);
        return new Prompt(prompts);
    }
}
