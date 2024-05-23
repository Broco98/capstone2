package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import start.capstone2.domain.Image.Image;
import start.capstone2.domain.Image.ImageStore;
import start.capstone2.domain.Image.S3Store;
import start.capstone2.domain.Image.repository.ImageRepository;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioApi;
import start.capstone2.domain.portfolio.PortfolioDetail;
import start.capstone2.domain.portfolio.repository.PortfolioDetailRepository;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.domain.user.User;
import start.capstone2.domain.user.repository.UserRepository;
import start.capstone2.dto.portfolio.PortfolioApiResponse;
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
    private final S3Store imageStore;

    @Transactional
    public Long createPortfolio(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Image image = Image.createEmptyImage();
        PortfolioDetail detail = PortfolioDetail.createEmptyDetail();
        Portfolio portfolio = Portfolio.createPortfolio(user, image, detail);
        portfolioRepository.save(portfolio);
        return portfolio.getId();
    }

    @Transactional
    public void updatePortfolio(Long userId, Long portfolioId, PortfolioRequest request) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();

        Image newImage = imageStore.saveImage(request.getImage());
        imageStore.removeImage(portfolio.getCardImage().getSavedName());

        portfolio.updatePortfolio(newImage, request.getStatus());
    }

    @Transactional
    public void deletePortfolio(Long userId, Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        imageStore.removeImage(portfolio.getCardImage().getSavedName());
        portfolioRepository.delete(portfolio);
    }

    private List<PortfolioResponse> getPortfolioResponses(List<Portfolio> portfolios) {
        List<PortfolioResponse> results = new ArrayList<>();
        for (Portfolio portfolio : portfolios) {
            results.add(new PortfolioResponse(portfolio.getId(), portfolio.getCardImage().getSavedName(), portfolio.getStatus()));
        }
        return results;
    }
    
    // 해당 유저의 모든 포트폴리오 조회
    public List<PortfolioResponse> findUserPortfolios(Long userId) {
        List<Portfolio> portfolios = portfolioRepository.findAllByUserId(userId);
        return getPortfolioResponses(portfolios);
    }
    
    // 공유된 모든 포트폴리오 조회
    public List<PortfolioResponse> findSharedPortfolios() {
        List<Portfolio> portfolios = portfolioRepository.findAllBySharedStatus();
        return getPortfolioResponses(portfolios);
    }
}
