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
import start.capstone2.dto.portfolio.PortfolioApiResponse;

import java.util.ArrayList;
import java.util.List;

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
                portfolio,
                request.getMethod(),
                request.getUrl(),
                request.getDescription(),
                request.getResponse()
        );

        portfolio.addApi(api);
        return api.getId();
    }

    @Transactional
    public void updatePortfolioApi(Long userId, Long portfolioId, Long apiId, PortfolioApiRequest request) {
        PortfolioApi api = apiRepository.findById(apiId).orElseThrow();
        api.updatePortfolioApi(
                request.getMethod(),
                request.getUrl(),
                request.getDescription(),
                request.getResponse()
        );
    }


    @Transactional
    public void deletePortfolioApi(Long userId, Long portfolioId, Long apiId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        PortfolioApi api = apiRepository.findById(apiId).orElseThrow();
        portfolio.removeApi(api);
    }

    public List<PortfolioApiResponse> findPortfolioApis(Long userId, Long portfolioId) {
        List<PortfolioApi> apis = apiRepository.findAllByPortfolioId(portfolioId);
        List<PortfolioApiResponse> results = new ArrayList<>();
        for (PortfolioApi api : apis) {
            results.add(new PortfolioApiResponse(api.getId(), api.getMethod(), api.getUrl(), api.getDescription(), api.getResponse()));
        }
        return results;
    }

}
