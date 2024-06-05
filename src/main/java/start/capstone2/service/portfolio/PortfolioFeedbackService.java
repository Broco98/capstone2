package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioApi;
import start.capstone2.domain.portfolio.PortfolioFeedback;
import start.capstone2.domain.portfolio.ShareStatus;
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

    @Transactional
    public Long createPortfolioFeedback(Long userId, Long portfolioId, PortfolioFeedbackRequest request) {

        User user = userRepository.findById(userId).orElseThrow();
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        if (!portfolio.getStatus().equals(ShareStatus.SHARED)) {
            throw new IllegalStateException("공유된 포트폴리오가 아닙니다!");
        }

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

    @Transactional
    public void updatePortfolioFeedback(Long userId, Long portfolioId, Long feedbackId, PortfolioFeedbackRequest request) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        if (!portfolio.getStatus().equals(ShareStatus.SHARED)) {
            throw new IllegalStateException("공유된 포트폴리오가 아닙니다!");
        }
        PortfolioFeedback feedback = portfolio.getFeedbacks().stream().filter(f -> f.getId().equals(feedbackId)).findFirst().orElseThrow();
        if (!feedback.getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가!");
        }
        feedback.updateFeedback(request.getContent(), request.getLocation());
    }
    
    @Transactional
    public void deletePortfolioFeedback(Long userId, Long portfolioId, Long feedbackId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        if (!portfolio.getStatus().equals(ShareStatus.SHARED)) {
            throw new IllegalStateException("공유된 포트폴리오가 아닙니다!");
        }
        PortfolioFeedback feedback = portfolio.getFeedbacks().stream().filter(f -> f.getId().equals(feedbackId)).findFirst().orElseThrow();
        if (!feedback.getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가!");
        }
        portfolio.removeFeedback(feedback);
    }
    
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
