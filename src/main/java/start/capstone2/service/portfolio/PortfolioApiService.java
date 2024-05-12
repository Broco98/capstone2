package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioApi;
import start.capstone2.dto.portfolio.PortfolioApiRequest;
import start.capstone2.domain.portfolio.repository.PortfolioApiRepository;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.domain.user.User;
import start.capstone2.domain.user.repository.UserRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PortfolioApiService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioApiRepository apiRepository;

    @Transactional
    public Long createPortfolioApi(Long userId, Long portfolioId, PortfolioApiRequest request) {
        User user = userRepository.findById(userId).orElseThrow();
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();

        PortfolioApi api = PortfolioApi.createPortfolioApi(
                request.getMethod(),
                request.getUrl(),
                request.getExplain(),
                request.getResponse()
        );

        portfolio.addApi(api);
        return api.getId();
    }

    @Transactional
    public void updatePortfolioApi(Long userId, Long portfolioId, Long apiId, PortfolioApi request) {
        PortfolioApi api = apiRepository.findById(apiId).orElseThrow();
        api.updatePortfolioApi(
                request.getMethod(),
                request.getUrl(),
                request.getExplain(),
                request.getResponse()
        );
    }


    @Transactional
    public void deletePortfolioApi(Long userId, Long portfolioId, Long apiId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        PortfolioApi api = apiRepository.findById(apiId).orElseThrow();
        portfolio.removeApi(api);
    }

}
