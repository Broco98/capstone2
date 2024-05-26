package start.capstone2.service.portfolio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioDetail;
import start.capstone2.domain.portfolio.PortfolioFunction;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.domain.user.repository.UserRepository;
import start.capstone2.dto.portfolio.PortfolioDetailRequest;
import start.capstone2.dto.portfolio.PortfolioDetailResponse;
import start.capstone2.dto.portfolio.PortfolioFunctionRequest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PortfolioDetailService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final OpenAiChatClient chatClient;

    @Transactional
    public Long createPortfolioDetail(Long userId, Long portfolioId, PortfolioDetailRequest request) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        PortfolioDetail detail = PortfolioDetail.createPortfolioDetail(request.getStartDate(), request.getEndDate(), request.getTeamNum(), request.getTitle(), request.getDescription(), request.getContribution());
        portfolio.setDetail(detail);
        return detail.getId();
    }

    @Transactional
    public void updatePortfolioDetail(Long userId, Long portfolioId, PortfolioDetailRequest request) {
        Portfolio portfolio = portfolioRepository.findByIdWithDetail(portfolioId);
        portfolio.getDetail().updatePortfolioDetail(
                request.getStartDate(),
                request.getEndDate(),
                request.getTeamNum(),
                request.getTitle(),
                request.getDescription(),
                request.getContribution()
        );
    }

    public PortfolioDetailResponse findPortfolioDetail(Long userId, Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findByIdWithDetail(portfolioId);
        PortfolioDetail detail = portfolio.getDetail();
        return new PortfolioDetailResponse(
                detail.getId(),
                detail.getTitle(),
                detail.getStartDate(),
                detail.getEndDate(),
                detail.getContribution(),
                detail.getDescription(),
                detail.getTeamNum()
        );
    }


    @Transactional
    public List<PortfolioFunctionRequest> generatePortfolioFunctionRequest(Long userId, Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findByIdWithDetail(portfolioId);
        PortfolioDetail detail = portfolio.getDetail();

        ChatResponse chatResponse = generateChatResponse(detail.getDescription());
        log.info("chatResponse={}",chatResponse);
        ObjectMapper mapper = new ObjectMapper();

        List<PortfolioFunctionRequest> functionRequests = new ArrayList<>();
        List<PortfolioFunction> functions = new ArrayList<>();
        try {
            JsonNode rootNode = mapper.readTree(chatResponse.toString());
            JsonNode contentNode = rootNode.path("data").path("content");
            functionRequests = mapper.readValue(contentNode.toString(), new TypeReference<List<PortfolioFunctionRequest>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return functionRequests;
    }


    private ChatResponse generateChatResponse(String message) {
        // 프롬프트 셋팅
        List<Message> prompts = new ArrayList<>();
        Message prompt1 = new SystemMessage("""
        너는 간단한 프로젝트 설명을 입력받으면 그 프로젝트에 필요한 기능명세서를 작성해야 돼.
        예를 들어서 입력으로 '간단한 게시판 프로젝트' 라고 입력받으면 게시판 프로젝트에 필요한 기능들을 기능별로 작성하는거야
        
        유저 기능 : 유저 생성, 유저 삭제, 유저 수정, 유저 조회
        게시판 기능 : 게시글 작성, 게시글 조회, 게시글 삭제, 게시글 스크랩, 게시글 좋아요, 게시글 수정
        
        이건 예시고 너가 알려줄 땐 각 기능에 설명을 주가해서 더 자세하게 알려줘
        답변은 json 형식으로 해주고, 기능별로 파싱할 수 있도록 나눠서 답변해줘야해
        
        이런 형식으로 파싱할거야 유저기능은 data[0].content, 게시판 기능은 data[0].content 이런 식으로 기능별로 접근 가능하도록
        참고로, Java의 objectMapper를 사용해서 파싱할거야
        
        이 결과를 바탕으로 실제로 설계를 진행할 수 있도록 최대한 자세하고 세밀하게 알려줘
        """);
        Message userMessage = new SystemMessage(message);

        // 프롬프트 추가
        prompts.add(prompt1);
        prompts.add(userMessage);

        // 생성
        Prompt prompt = new Prompt(prompts);
        return chatClient.call(prompt);
    }
}
