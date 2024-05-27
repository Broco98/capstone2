package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioApi;
import start.capstone2.domain.portfolio.PortfolioFeedback;
import start.capstone2.dto.portfolio.PortfolioApiResponse;
import start.capstone2.dto.portfolio.PortfolioFeedbackRequest;
import start.capstone2.domain.portfolio.repository.PortfolioFeedbackRepository;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.domain.user.User;
import start.capstone2.domain.user.repository.UserRepository;
import start.capstone2.dto.portfolio.PortfolioFeedbackResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PortfolioFeedbackService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioFeedbackRepository feedbackRepository;

    // TODO user 확인 필요
    @Transactional
    public Long createPortfolioFeedback(Long userId, Long portfolioId, PortfolioFeedbackRequest request) {

        User user = userRepository.findById(userId).orElseThrow();
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();

        PortfolioFeedback portfolioFeedback = PortfolioFeedback.builder()
                .user(user)
                .portfolio(portfolio)
                .content(request.getContent())
                .page(request.getPage())
                .location(request.getLocation())
                .build();

        portfolio.addFeedback(portfolioFeedback);
        return portfolioFeedback.getId();
    }

    // TODO user 확인 필요
    @Transactional
    public void updatePortfolioFeedback(Long userId, Long feedbackId, PortfolioFeedbackRequest request) {

        PortfolioFeedback feedback = feedbackRepository.findById(feedbackId).orElseThrow();

        feedback.updateFeedback(
                request.getContent(),
                request.getLocation()
        );
    }
    
    // TODO user 확인 필요
    @Transactional
    public void deletePortfolioFeedback(Long userId, Long portfolioId, Long feedbackId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        PortfolioFeedback feedback = feedbackRepository.findById(feedbackId).orElseThrow();
        portfolio.removeFeedback(feedback);
    }
    
    // TODO user 확인 필요
    // portfolio의 모든 feedback 조회
    public List<PortfolioFeedbackResponse> findPortfolioFeedbacks(Long userId, Long portfolioId) {
        List<PortfolioFeedback> feedbacks = feedbackRepository.findAllByPortfolioId(portfolioId);
        List<PortfolioFeedbackResponse> results = new ArrayList<>();
        for (PortfolioFeedback feedback : feedbacks) {
            results.add(new PortfolioFeedbackResponse(feedback.getId(), feedback.getContent(), feedback.getPage(), feedback.getLocation()));
        }
        return results;
    }
}
