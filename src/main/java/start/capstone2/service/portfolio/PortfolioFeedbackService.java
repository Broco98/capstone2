package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.Image.ImageStore;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioFeedback;
import start.capstone2.domain.portfolio.dto.PortfolioFeedbackRequest;
import start.capstone2.domain.portfolio.repository.PortfolioFeedbackRepository;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.domain.user.User;
import start.capstone2.domain.user.repository.GroupRepository;
import start.capstone2.domain.user.repository.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PortfolioFeedbackService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioFeedbackRepository portfolioFeedbackRepository;

    @Transactional
    public Long createPortfolioFeedback(Long userId, Long portfolioId, PortfolioFeedbackRequest portfolioFeedbackRequest) {

        User user = userRepository.findById(userId).orElseThrow();
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();

        PortfolioFeedback portfolioFeedback = PortfolioFeedback.createPortfolioFeedback(
                user,
                portfolio,
                portfolioFeedbackRequest.getContent(),
                portfolioFeedbackRequest.getLocation()
        );

        portfolio.addFeedback(portfolioFeedback);
        return portfolioFeedback.getId();
    }

    @Transactional
    public void updatePortfolioFeedback(Long userId, Long portfolioId, Long feedbackId, PortfolioFeedbackRequest request) {

        User user = userRepository.findById(userId).orElseThrow();
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        PortfolioFeedback feedback = portfolioFeedbackRepository.findById(feedbackId).orElseThrow();

        feedback.updateFeedback(
                request.getContent(),
                request.getLocation()
        );
    }

    public List<PortfolioFeedback> findAllPortfolioFeedbacks(Long portfolioId) {
        return portfolioFeedbackRepository.findAllByPortfolioId(portfolioId);
    }

    @Transactional
    public void deletePortfolioFeedback(Long userId, Long portfolioId, Long feedbackId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        PortfolioFeedback feedback = portfolioFeedbackRepository.findById(feedbackId).orElseThrow();
        portfolio.removeFeedback(feedback);
    }

}
