package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioClipping;
import start.capstone2.domain.portfolio.ShareStatus;
import start.capstone2.domain.portfolio.repository.PortfolioClippingRepository;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.domain.user.User;
import start.capstone2.domain.user.repository.UserRepository;
import start.capstone2.dto.portfolio.PortfolioResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortfolioClippingService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioClippingRepository clippingRepository;

    @Transactional
    public Long createPortfolioClipping(Long userId, Long portfolioId) {

        User user = userRepository.findById(userId).orElseThrow();
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        if (!portfolio.getStatus().equals(ShareStatus.SHARED)) {
            throw new IllegalStateException("공유된 포트폴리오가 아닙니다.");
        }
        PortfolioClipping clipping = PortfolioClipping.builder()
                .portfolio(portfolio)
                .user(user)
                .build();

        clippingRepository.save(clipping);

        return clipping.getId();
    }

    @Transactional
    public void deletePortfolioClipping(Long userId, Long clippingId) {
        PortfolioClipping clipping = clippingRepository.findById(clippingId).orElseThrow();
        if (!clipping.getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가");
        }
        clippingRepository.delete(clipping);
    }

    // 유저가 스크랩한 모든 포트폴리오 조회
    public List<PortfolioResponse> findAllClippingPortfolio(Long userId) {
        List<PortfolioClipping> clippings = clippingRepository.findAllByUserId(userId);
        List<Portfolio> portfolios = new ArrayList<>();
        List<PortfolioResponse> results = new ArrayList<>();
        for (PortfolioClipping clipping : clippings) {
            Portfolio portfolio = clipping.getPortfolio();
            portfolios.add(portfolio);
        }

        return getPortfolioResponses(portfolios);
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
