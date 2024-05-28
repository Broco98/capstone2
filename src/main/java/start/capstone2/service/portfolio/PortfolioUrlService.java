package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioFunction;
import start.capstone2.domain.portfolio.PortfolioUrl;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.domain.portfolio.repository.PortfolioUrlRepository;
import start.capstone2.domain.url.Url;
import start.capstone2.domain.url.repository.UrlRepository;
import start.capstone2.domain.user.User;
import start.capstone2.domain.user.repository.UserRepository;
import start.capstone2.dto.portfolio.PortfolioFunctionRequest;
import start.capstone2.dto.portfolio.PortfolioFunctionResponse;
import start.capstone2.dto.portfolio.PortfolioUrlRequest;
import start.capstone2.dto.portfolio.PortfolioUrlResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortfolioUrlService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioUrlRepository portfolioUrlRepository;
    private final UrlRepository urlRepository;

    // TODO user 확인 필요
    @Transactional
    public Long createPortfolioUrl(Long userId, Long portfolioId, Long urlId) {
        User user = userRepository.findById(userId).orElseThrow();
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        Url url = urlRepository.findById(urlId).orElseThrow();

        PortfolioUrl portfolioUrl = PortfolioUrl.builder()
                .portfolio(portfolio)
                .url(url)
                .build();

        portfolio.addUrl(portfolioUrl);
        url.addPortfolioUrl(portfolioUrl);
        return portfolioUrl.getId();
    }

    // TODO user 확인 필요
    @Transactional
    public void deletePortfolioUrl(Long userId, Long portfolioId, Long urlId, Long portfolioUrlId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        PortfolioUrl portfolioUrl = portfolioUrlRepository.findById(portfolioUrlId).orElseThrow();
        Url url = urlRepository.findById(urlId).orElseThrow();

        portfolio.removeUrl(portfolioUrl);
        url.removePortfolioUrl(portfolioUrl);
    }
    
    // TODO 미완
    public List<PortfolioUrlResponse> findAllByPortfolioId(Long userId, Long portfolioId) {
        List<PortfolioUrl> urls = portfolioUrlRepository.findAllByPortfolioIdWithUrl(portfolioId);
        List<PortfolioUrlResponse> results = new ArrayList<>();
        for (PortfolioUrl portfolioUrl : urls) {
            Url url = portfolioUrl.getUrl();
            results.add(new PortfolioUrlResponse(url.getId(), url.getUrl()));
        }
        return results;
    }

}
