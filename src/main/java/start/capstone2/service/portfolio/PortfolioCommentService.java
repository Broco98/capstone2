package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioComment;
import start.capstone2.dto.portfolio.PortfolioCommentRequest;
import start.capstone2.domain.portfolio.repository.PortfolioCommentRepository;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.domain.user.User;
import start.capstone2.domain.user.repository.UserRepository;
import start.capstone2.dto.portfolio.PortfolioCommentResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PortfolioCommentService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioCommentRepository commentRepository;
    
    // TODO user 정보 필요
    @Transactional
    public Long createPortfolioComment(Long userId, Long portfolioId, PortfolioCommentRequest request) {
        User user = userRepository.findById(userId).orElseThrow();
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();

        PortfolioComment comment = PortfolioComment.createPortfolioComment(
                user,
                portfolio,
                request.getContent()
        );

        portfolio.addComment(comment);
        return comment.getId();
    }

    // TODO user 정보 필요
    @Transactional
    public void updatePortfolioComment(Long userId, Long portfolioId, Long commentId, PortfolioCommentRequest request) {
        PortfolioComment comment = commentRepository.findById(commentId).orElseThrow();
        comment.updateContent(request.getContent());
    }


    // TODO user 정보 필요
    @Transactional
    public void deletePortfolioComment(Long userId, Long portfolioId, Long commentId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        PortfolioComment comment = commentRepository.findById(commentId).orElseThrow();
        portfolio.removeComment(comment);
    }

    // TODO user 정보 필요
    // 유저의 comments 조회
    public List<PortfolioCommentResponse> findPortfolioComments(Long userId, Long portfolioId) {
        List<PortfolioComment> comments = commentRepository.findAllByPortfolioId(portfolioId);
        List<PortfolioCommentResponse> responses = new ArrayList<>();
        for (PortfolioComment comment : comments) {
            responses.add(new PortfolioCommentResponse(comment.getId(), comment.getContent()));
        }
        return responses;
    }
}
