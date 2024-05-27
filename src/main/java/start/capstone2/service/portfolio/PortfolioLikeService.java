package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioClipping;
import start.capstone2.domain.portfolio.PortfolioInterview;
import start.capstone2.domain.portfolio.PortfolioLike;
import start.capstone2.domain.portfolio.repository.PortfolioInterviewRepository;
import start.capstone2.domain.portfolio.repository.PortfolioLikeRepository;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.domain.user.User;
import start.capstone2.domain.user.repository.UserRepository;
import start.capstone2.dto.portfolio.PortfolioInterviewRequest;
import start.capstone2.dto.portfolio.PortfolioInterviewResponse;
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


    // TODO user 확인 필요
    @Transactional
    public Long createPortfolioLike(Long userId, Long portfolioId) {

        User user = userRepository.findById(userId).orElseThrow();
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();

        PortfolioLike like = PortfolioLike.builder()
                .portfolio(portfolio)
                .user(user)
                .build();

        likeRepository.save(like);

        return like.getId();
    }

    // TODO user 확인 필요
    @Transactional
    public void deletePortfolioLike(Long userId, Long clippingId) {
        likeRepository.deleteById(clippingId);
    }

    // TODO user 확인 필요
    // user가 스크랩한 portfolio 모두 조회
    public List<PortfolioResponse> findPortfolioLike(Long userId) {
        List<PortfolioLike> likes = likeRepository.findAllByUserId(userId);
        List<PortfolioResponse> results = new ArrayList<>();
        for (PortfolioLike like : likes) {
            Portfolio portfolio = like.getPortfolio();
            results.add(new PortfolioResponse(portfolio.getId(), portfolio.getCardImage().getSavedName(), portfolio.getStatus()));
        }

        return results;
    }

}
