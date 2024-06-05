package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioLike;
import start.capstone2.domain.portfolio.ShareStatus;
import start.capstone2.domain.portfolio.repository.PortfolioLikeRepository;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.domain.user.User;
import start.capstone2.domain.user.repository.UserRepository;
import start.capstone2.dto.portfolio.PortfolioResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortfolioLikeService {

    private final PortfolioLikeRepository likeRepository;
    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;


    @Transactional
    public Long createPortfolioLike(Long userId, Long portfolioId) {

        User user = userRepository.findById(userId).orElseThrow();
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        if (!portfolio.getStatus().equals(ShareStatus.SHARED)) {
            throw new IllegalStateException("공유된 포트폴리오가 아닙니다.");
        }

        PortfolioLike like = PortfolioLike.builder()
                .portfolio(portfolio)
                .user(user)
                .build();

        likeRepository.save(like);
        return like.getId();
    }

    @Transactional
    public void deletePortfolioLike(Long userId, Long likeId) {
        PortfolioLike like = likeRepository.findById(likeId).orElseThrow();
        if (!like.getPortfolio().getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가!");
        }
        likeRepository.delete(like);
    }

    // 유저가 좋아요한 포트폴리오 모두 조회
    public List<PortfolioResponse> findAllLikePortfolio(Long userId) {
        List<PortfolioLike> likes = likeRepository.findAllByUserId(userId);
        List<Portfolio> results = new ArrayList<>();
        for (PortfolioLike like : likes) {
            Portfolio portfolio = like.getPortfolio();
            results.add(portfolio);
        }

        return getPortfolioResponses(results);
    }

    private List<PortfolioResponse> getPortfolioResponses(List<Portfolio> portfolios) {
        List<PortfolioResponse> results = new ArrayList<>();
        for (Portfolio portfolio : portfolios) {

            String imageUrl = null;
            if (portfolio.getCardImage() != null) {
                imageUrl = portfolio.getCardImage().getSavedName();
            }

            results.add(new PortfolioResponse(
                    portfolio.getId(),
                    imageUrl,
                    portfolio.getTitle(),
                    portfolio.getStartDate(),
                    portfolio.getEndDate(),
                    portfolio.getTeamNum(),
                    portfolio.getDescription(),
                    portfolio.getContribution(),
                    portfolio.getTechStacks(),
                    portfolio.getStatus()));
        }
        return results;
    }

}
