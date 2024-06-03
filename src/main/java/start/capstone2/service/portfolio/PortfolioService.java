package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.Image.Image;
import start.capstone2.domain.Image.S3Store;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.domain.user.User;
import start.capstone2.domain.user.repository.UserRepository;
import start.capstone2.dto.portfolio.PortfolioRequest;
import start.capstone2.dto.portfolio.PortfolioResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PortfolioService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final S3Store store;
    
    @Transactional
    public Long createPortfolio(Long userId, PortfolioRequest request) {

        Image image = null;
        if (request.getImage() != null) {
            image = store.saveImage(request.getImage());
        }

        Portfolio portfolio = Portfolio.builder()
//                .user(user)
                .cardImage(image)
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .contribution(request.getContribution())
                .title(request.getTitle())
                .teamNum(request.getTeamNum())
                .build();

        portfolioRepository.save(portfolio);
        return portfolio.getId();
    }

    // TODO user 정보 필요
    @Transactional
    public void updatePortfolio(Long userId, Long portfolioId, PortfolioRequest request) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();

        Image image = null;
        if (request.getImage() != null) {
            image = store.saveImage(request.getImage());
        }

        if (portfolio.getCardImage() != null) {
            store.removeImage(portfolio.getCardImage().getSavedName());
        }

        portfolio.updatePortfolio(
                request.getTitle(),
                request.getStartDate(),
                request.getEndDate(),
                request.getTeamNum(),
                request.getDescription(),
                request.getContribution(),
                image,
                request.getStatus());
    }

    // TODO user 정보 필요
    @Transactional
    public void deletePortfolio(Long userId, Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        store.removeImage(portfolio.getCardImage().getSavedName());
        portfolioRepository.delete(portfolio);
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
                    portfolio.getStatus()));
        }
        return results;
    }
    
    // TODO user 정보 필요
    // 해당 유저의 모든 포트폴리오 조회
    public List<PortfolioResponse> findAllByUserId(Long userId) {
        List<Portfolio> portfolios = portfolioRepository.findAllByUserId(userId);
        return getPortfolioResponses(portfolios);
    }

    // TODO user 정보 필요
    // 공유된 모든 포트폴리오 조회
    public List<PortfolioResponse> findAllBySharedStatus() {
        List<Portfolio> portfolios = portfolioRepository.findAllBySharedStatus();
        return getPortfolioResponses(portfolios);
    }

    public Portfolio findPortfolioById(Long portfolioId) {
        return portfolioRepository.findById(portfolioId).orElseThrow();
    }

    // 단일 조회
    public PortfolioResponse findById(Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();

        String imageUrl = null;
        if (portfolio.getCardImage() != null) {
            imageUrl = portfolio.getCardImage().getSavedName();
        }

        return new PortfolioResponse(
                portfolio.getId(),
                imageUrl,
                portfolio.getTitle(),
                portfolio.getStartDate(),
                portfolio.getEndDate(),
                portfolio.getTeamNum(),
                portfolio.getDescription(),
                portfolio.getContribution(),
                portfolio.getStatus());
    }

}
