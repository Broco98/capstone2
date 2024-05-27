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
import start.capstone2.dto.portfolio.PortfolioResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UrlService {

    private final UserRepository userRepository;
    private final PortfolioUrlRepository portfolioUrlRepository;
    private final UrlRepository urlRepository;

    // TODO user 조회 필요
    // 빈 url 생성
    @Transactional
    public Long createUrl(Long userId,  UrlRequest request) {
        User user = userRepository.findById(userId).orElseThrow();
        Url url = Url.builder().url(UUID.randomUUID().toString()).build();

        urlRepository.save(url);
        return url.getId();
    }

    // TODO user 조회 필요
    @Transactional
    public void deleteUrl(Long userId, Long urlId) {
        Url url = urlRepository.findById(urlId).orElseThrow();
        urlRepository.delete(url);
    }

    // TODO user 조회 필요
    // url 에 있는 모든 portfolio 조회
    public List<Portfolio> findAllById(Long userId, Long urlId) {
        Url url = urlRepository.findById(urlId).orElseThrow();
        List<PortfolioUrl> portfolioUrls = url.getPortfolioUrls();
        List<Portfolio> results = new ArrayList<>();
        for (PortfolioUrl portfolioUrl : portfolioUrls) {
            Portfolio portfolio = portfolioUrl.getPortfolio();
            results.add(portfolio);
        }

        return results;
    }
}
