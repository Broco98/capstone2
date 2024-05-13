package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.Image.Image;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.repository.PortfolioDetailRepository;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.domain.user.User;
import start.capstone2.domain.user.repository.UserRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PortfolioService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;

    public Long createPortfolio(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Portfolio portfolio = Portfolio.createPortfolio(user, null, null);
        portfolioRepository.save(portfolio);
        return portfolio.getId();
    }

    public void updateCardImage(Long userId, Long portfolioId, Image cardImage) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        portfolio.updateCardImage(cardImage);
    }

    public void deletePortfolio(Long userId, Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        portfolio.remove();
        portfolioRepository.delete(portfolio);
    }

}
