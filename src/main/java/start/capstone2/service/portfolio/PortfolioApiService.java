package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioApi;
import start.capstone2.domain.portfolio.PortfolioApiModule;
import start.capstone2.domain.portfolio.repository.PortfolioApiRepository;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.domain.user.repository.UserRepository;
import start.capstone2.dto.portfolio.PortfolioApiModuleResponse;
import start.capstone2.dto.portfolio.PortfolioApiRequest;
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
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        if (!portfolio.getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가");
        }
        PortfolioApi api = PortfolioApi.builder()
                .name(request.getName())
                .portfolio(portfolio)
                .build();

        portfolio.addApi(api);
        return api.getId();
    }
    
    @Transactional
    public void updatePortfolioApi(Long userId, Long portfolioId, Long apiId, PortfolioApiRequest request) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        if (!portfolio.getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가");
        }
        PortfolioApi api = portfolio.getApis().stream().filter(a->a.getId().equals(apiId)).findFirst().orElseThrow();
        api.updatePortfolioApi(request.getName());
    }

    @Transactional
    public void deletePortfolioApi(Long userId, Long portfolioId, Long apiId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        if (!portfolio.getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가");
        }
        PortfolioApi api = portfolio.getApis().stream().filter(a->a.getId().equals(apiId)).findFirst().orElseThrow();
        portfolio.removeApi(api);
    }

    // 포트폴리오의 모든 api 명세 조회
    public List<PortfolioApiResponse> findPortfolioApis(Long userId, Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        if (!portfolio.getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가");
        }
        List<PortfolioApi> apis = portfolio.getApis();
        List<PortfolioApiResponse> results = new ArrayList<>();
        for (PortfolioApi api : apis) {
            List<PortfolioApiModule> modules = api.getModules();
            List<PortfolioApiModuleResponse> moduleResponses = new ArrayList<>();
            for (PortfolioApiModule module : modules) {
                PortfolioApiModuleResponse moduleResponse = PortfolioApiModuleResponse.builder()
                        .response(module.getResponse())
                        .url(module.getUrl())
                        .id(module.getId())
                        .method(module.getMethod())
                        .description(module.getDescription())
                        .build();
                moduleResponses.add(moduleResponse);
            }
            results.add(new PortfolioApiResponse(api.getId(), api.getName(), moduleResponses));
        }
        return results;
    }



}
