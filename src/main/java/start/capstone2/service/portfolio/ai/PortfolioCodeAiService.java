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
        You need to generate (write) the project code when given the project information as input. You should only generate the code for the file provided by the user.
        Consider the file names and descriptions in the === Project Code Structure === for creating consistent and integrated code.
        
        The input may contain duplicate Table information, but you need to consolidate this into a single Table and use it.
        
        The expected response should be in the following JSON format:
        
        {
         "data" : [
             {
                 "name" : (The name of the file in the project code structure provided by the user. Modify if necessary.),
                 "code" : (The part you need to generate, including detailed explanations in 'Korean comments', in String format.),
                 "description" : (The description of the file in the project code structure provided by the user, in String format. Modify if necessary.)
             }
         ]
        }
        
        The JSON response must meet the following conditions:
        
        1. All data must be inside the "data" array,
        2. The array must contain the file information provided by the user (name, code, description),
        3. It must be accessible in the format data[0].name, data[0].description, and so on,
        4. data[0].name should return the name of the file,
        5. You should only generate code for the file provided by the user, meaning a single JSON response is expected,
        6. Consider consistency and integration.
     
        The generated code will be used to create the actual project, so ensure good integration and consistency.
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
        When you receive the project information as input, you need to generate (create) the project code structure (files). The tech-stack of the project should guide the creation of the files. For example, if it's Java, you should create files with .java or .class extensions.
        Note that there may be duplicates of classes or tables in the project diagram or project DB schema. This happens because diagrams and schemas are created for each feature. For example, the User feature may have User tables and classes, and the Chat feature may also have User tables and classes.
       
        Consider this and think of a unified table and class that can satisfy all feature specifications, even if duplicate tables are input. For example, if User1 and User2 tables and classes are input, you should use a User3 table, considering the functionalities. You should not use duplicate tables! For instance, you shouldn't use both User1 and User2; only one User table should be used.
       
        {
               "data" : [
               {
                       "name" : (the required file name in English),
                       "description" : (description of the file, explained in Korean, String format)
               },
               {
                     "name" : (the required file name in English),
                     "description" : (description of the file, explained in Korean, String format)
               },
               ... (repeat for as many files as you need, no duplicates)
           ]
        }
       
        Expect a response in this JSON format:
       
        1. All data should be within the "data" array,
        2. Inside, there should be multiple sets of file information (name, description),
        3. It should be accessible like data[0].name, data[0].description, data[1].name, and so on,
        4. Accessing data[0].name should return the file's name,
        5. Provide as many pairs of name and description as you deem necessary based on the structure information of the code (name, description).
        
        Provide as detailed and precise information as possible to proceed with actual coding based on this response. This response will be used to generate code.
        """);
        Message userMessage = new SystemMessage(message);

        prompts.add(prompt);
        prompts.add(userMessage);
        return new Prompt(prompts);
    }
}
