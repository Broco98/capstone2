package start.capstone2.service.portfolio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.Media;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;
import start.capstone2.domain.Image.Image;
import start.capstone2.domain.Image.S3Store;
import start.capstone2.domain.portfolio.PortfolioDesign;
import start.capstone2.domain.portfolio.PortfolioDesignDiagram;
import start.capstone2.domain.portfolio.repository.PortfolioDesignRepository;
import start.capstone2.dto.portfolio.PortfolioDesignDiagramRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PortfolioDesignDiagramService {

    private final PortfolioDesignRepository designRepository;
    private final S3Store store;
    private final ChatClient client;

    @Transactional
    public Long createPortfolioDesignDiagram(Long userId, Long designId, PortfolioDesignDiagramRequest request) {

        PortfolioDesign design = designRepository.findById(designId).orElseThrow();
        if (!design.getPortfolio().getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가!");
        }
        
        // 이미지 확인
        Image image = null;
        if (request.getImage() != null) {
            image = store.saveImage(request.getImage());
        }
        
        // 이미지만 있고 uml문이 없는 경우
        String diagramData = request.getDiagram();
        if (request.getDiagram() == null && image != null) {
            diagramData = readImageForGenerateUMLString(image);
        }

        // uml문만 있고 이미지가 없는 경우
        if (diagramData != null && image == null) {
            image = store.saveImage(createDiagramImage(diagramData));
        }

        PortfolioDesignDiagram diagram = PortfolioDesignDiagram.builder()
                .image(image)
                .diagram(diagramData)
                .description(request.getDescription())
                .build();

        design.addDiagram(diagram);
        return diagram.getId();
    }
    
    @Transactional
    public void updatePortfolioDesignSchema(Long userId, Long designId, Long diagramId, PortfolioDesignDiagramRequest request) {
        PortfolioDesign design = designRepository.findById(designId).orElseThrow();
        if (!design.getPortfolio().getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가!");
        }
        PortfolioDesignDiagram diagram = design.getDiagrams().stream()
                .filter(d -> d.getId().equals(diagramId))
                .findFirst().orElseThrow();

        Image image = null;
        if (request.getImage() != null) {
            image = store.saveImage(request.getImage());
        }

        if (diagram.getImage() != null) {
            store.removeImage(diagram.getImage().getSavedName());
        }

        diagram.updatePortfolioDesignDiagram(request.getDiagram(), image, request.getDescription());
    }
    
    @Transactional
    public void deletePortfolioDesignDiagram(Long userId, Long designId, Long diagramId) {
        PortfolioDesign design = designRepository.findById(designId).orElseThrow();
        if (!design.getPortfolio().getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가!");
        }
        PortfolioDesignDiagram diagram = design.getDiagrams().stream().filter(d->d.getId().equals(diagramId)).findFirst().orElseThrow();
        if (diagram.getImage() != null) {
            store.removeImage(diagram.getImage().getSavedName());
        }
        design.removeDiagram(diagram);
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

    private String readImageForGenerateUMLString(Image image) {
        List<Message> messages = new ArrayList<>();
        Message sysMessage = new SystemMessage("""
        이 UML 이미지를 읽고 plantUML String 값으로 반환해줘
        
        json 형식으로 반환해야 하고 예시로,
        
        {
            "data" : (plantUML String값)
        }
        
        이렇게 보내주면 돼.
        
        """);

        InputStream inputStream = store.findImageBytes(image.getSavedName());
        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
        Message userMessage = new UserMessage(inputStreamResource);
        messages.add(sysMessage);
        messages.add(userMessage);

        String json = client.call(new Prompt(messages)).getResult().getOutput().getContent();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            return jsonNode.get("data").asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    //    // 해당 포트폴리오의 모든 디자인 조회
//    public List<PortfolioDesignResponse> findPortfolioDesigns(Long userId, Long portfolioId) {
//        List<PortfolioDesign> designs = designRepository.findAllByPortfolioId(portfolioId);
//        List<PortfolioDesignResponse> results = new ArrayList<>();
//        for (PortfolioDesign design : designs) {
//            results.add(new PortfolioDesignResponse(design.getId(), design.getDesign(), design.getDescription()));
//        }
//        return results;
//    }

}
