package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioClipping;
import start.capstone2.domain.portfolio.repository.PortfolioClippingRepository;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.domain.user.User;
import start.capstone2.domain.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortfolioClippingService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioClippingRepository clippingRepository;

    // TODO user 확인 필요
    @Transactional
    public Long createPortfolioClipping(Long userId, Long portfolioId) {

        User user = userRepository.findById(userId).orElseThrow();
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();

        PortfolioClipping clipping = PortfolioClipping.builder()
                .portfolio(portfolio)
                .user(user)
                .build();

        clippingRepository.save(clipping);

        return clipping.getId();
    }

    // TODO user 확인 필요
    @Transactional
    public void deletePortfolioClipping(Long userId, Long clippingId) {
        clippingRepository.deleteById(clippingId);
    }

    // TODO user 확인 필요
    // user가 스크랩한 portfolio 모두 조회
    public List<Portfolio> findPortfolioClippings(Long userId) {
        List<PortfolioClipping> clippings = clippingRepository.findAllByUserId(userId);
        List<Portfolio> results = new ArrayList<>();
        for (PortfolioClipping clipping : clippings) {
            Portfolio portfolio = clipping.getPortfolio();
            results.add(portfolio);
        }

        return results;
    }

}
