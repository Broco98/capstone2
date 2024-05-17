package start.capstone2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioUrl;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.domain.portfolio.repository.PortfolioUrlRepository;
import start.capstone2.domain.url.Url;
import start.capstone2.domain.url.repository.UrlRepository;
import start.capstone2.domain.user.User;
import start.capstone2.domain.user.repository.UserRepository;
import start.capstone2.dto.UrlRequest;
import start.capstone2.dto.UserRequest;
import start.capstone2.dto.portfolio.PortfolioResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UrlService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioUrlRepository portfolioUrlRepository;
    private final UrlRepository urlRepository;

    @Transactional
    public Long createUrl(Long userId,  UrlRequest request) {
        User user = userRepository.findById(userId).orElseThrow();
        Url url = Url.createUrl(UUID.randomUUID().toString()); // random url 생성
        List<Portfolio> portfolios = portfolioRepository.findAllById(request.getPortfolioIds());

        for (Portfolio portfolio : portfolios) {
            PortfolioUrl portfolioUrl = PortfolioUrl.createPortfolioUrl(portfolio, url);
            portfolio.addUrl(portfolioUrl);
            url.addPortfolioUrl(portfolioUrl);
        }

        urlRepository.save(url);
        return url.getId();
    }

    @Transactional
    public void updateUrl(Long userId, Long urlId, UrlRequest request) {
        Url url = urlRepository.findById(urlId).orElseThrow();
        List<PortfolioUrl> portfolioUrls = url.getPortfolioUrls();

        List<Portfolio> newPortfolios = portfolioRepository.findAllById(request.getPortfolioIds());
        List<PortfolioUrl> newPortfolioUrls = new ArrayList<>();
        for (Portfolio portfolio : newPortfolios) {
            PortfolioUrl portfolioUrl = PortfolioUrl.createPortfolioUrl(portfolio, url);
            portfolio.addUrl(portfolioUrl);
            newPortfolioUrls.add(portfolioUrl);
        }

        url.getPortfolioUrls().clear();
        url.getPortfolioUrls().addAll(newPortfolioUrls);
    }

    @Transactional
    public void deleteUrl(Long userId, Long urlId) {
        Url url = urlRepository.findById(urlId).orElseThrow();
        urlRepository.delete(url);
    }

    // TODO 최적화 필요
    public List<PortfolioResponse> findAllById(Long userId, Long urlId) {
        Url url = urlRepository.findById(urlId).orElseThrow();
        List<PortfolioUrl> portfolioUrls = url.getPortfolioUrls();
        List<PortfolioResponse> results = new ArrayList<>();

        for (PortfolioUrl portfolioUrl : portfolioUrls) {
            Portfolio portfolio = portfolioUrl.getPortfolio();
            results.add(new PortfolioResponse(portfolio.getId(), portfolio.getCardImage().getSavedName(), portfolio.getStatus()));
        }

        return results;
    }
}
