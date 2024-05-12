package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioComment;
import start.capstone2.domain.portfolio.PortfolioDesign;
import start.capstone2.domain.portfolio.repository.PortfolioCommentRepository;
import start.capstone2.domain.portfolio.repository.PortfolioDesignRepository;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.domain.user.User;
import start.capstone2.domain.user.repository.UserRepository;
import start.capstone2.dto.portfolio.PortfolioCommentRequest;
import start.capstone2.dto.portfolio.PortfolioDesignRequest;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PortfolioDesignService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioDesignRepository designRepository;


    @Transactional
    public Long createPortfolioDesign(Long userId, Long portfolioId, PortfolioDesignRequest request) {
        User user = userRepository.findById(userId).orElseThrow();
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();

        // TODO imageStore 사용해야 함
        PortfolioDesign design = PortfolioDesign.createPortfolioDesign(
                request.getImage()
        );

        portfolio.addComment(comment);
        return comment.getId();
    }

    @Transactional
    public void updatePortfolioComment(Long userId, Long portfolioId, Long commentId, PortfolioCommentRequest request) {
        PortfolioComment comment = commentRepository.findById(commentId).orElseThrow();
        comment.updateContent(request.getContent());
    }


    @Transactional
    public void deletePortfolioComment(Long userId, Long portfolioId, Long commentId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        PortfolioComment comment = commentRepository.findById(commentId).orElseThrow();
        portfolio.removeComment(comment);
    }


}
