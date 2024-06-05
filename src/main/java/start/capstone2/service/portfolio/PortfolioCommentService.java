package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioComment;
import start.capstone2.domain.portfolio.ShareStatus;
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
    
    @Transactional
    public Long createPortfolioComment(Long userId, Long portfolioId, PortfolioCommentRequest request) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        if (!portfolio.getStatus().equals(ShareStatus.SHARED)) {
            throw new IllegalStateException("공유된 포트폴리오가 아닙니다!");
        }

        User user = userRepository.findById(userId).orElseThrow();

        PortfolioComment comment = PortfolioComment.builder()
                .user(user)
                .portfolio(portfolio)
                .content(request.getContent())
                .build();

        portfolio.addComment(comment);
        return comment.getId();
    }

    @Transactional
    public void updatePortfolioComment(Long userId, Long portfolioId, Long commentId, PortfolioCommentRequest request) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        if (!portfolio.getStatus().equals(ShareStatus.SHARED)) {
            throw new IllegalStateException("공유된 포트폴리오가 아닙니다!");
        }
        PortfolioComment comment = portfolio.getComments().stream().filter(c->c.getId().equals(commentId)).findFirst().orElseThrow();
        if (!comment.getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가");
        }

        comment.updateContent(request.getContent());
    }

    @Transactional
    public void deletePortfolioComment(Long userId, Long portfolioId, Long commentId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        if (!portfolio.getStatus().equals(ShareStatus.SHARED)) {
            throw new IllegalStateException("공유된 포트폴리오가 아닙니다!");
        }
        PortfolioComment comment = portfolio.getComments().stream().filter(c->c.getId().equals(commentId)).findFirst().orElseThrow();
        if (!comment.getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가");
        }
        portfolio.removeComment(comment);
    }

    // 해당 포트폴리오의 모든 comment 조회
    public List<PortfolioCommentResponse> findPortfolioComments(Long userId, Long portfolioId) {
        List<PortfolioComment> comments = commentRepository.findAllByPortfolioId(portfolioId);
        List<PortfolioCommentResponse> responses = new ArrayList<>();
        for (PortfolioComment comment : comments) {
            responses.add(new PortfolioCommentResponse(comment.getId(), comment.getContent()));
        }
        return responses;
    }
}
