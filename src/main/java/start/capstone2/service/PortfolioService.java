package start.capstone2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.portfolio.repository.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PortfolioService {

    // TODO: Member
    private final PortfolioRepository portfolioRepository;
    private final PortfolioCardImageRepository cardRepository;
    private final PortfolioCommentRepository commentRepository;
    private final PortfolioFeedbackRepository feedbackRepository;
    private final PortfolioImageRepository imageRepository;
    private final PortfolioPostRepository postRepository;

    @Transactional
    public void createPortfolio() {

    }


}
