package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.portfolio.PortfolioApi;
import start.capstone2.domain.portfolio.PortfolioApiModule;
import start.capstone2.domain.portfolio.repository.PortfolioApiModuleRepository;
import start.capstone2.domain.portfolio.repository.PortfolioApiRepository;
import start.capstone2.dto.portfolio.PortfolioApiModuleRequest;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PortfolioApiModuleService {

    private final PortfolioApiRepository apiRepository;
    private final PortfolioApiModuleRepository moduleRepository;

    @Transactional
    public Long createPortfolioApiModule(Long userId, Long apiId, PortfolioApiModuleRequest request) {

        PortfolioApi api = apiRepository.findById(apiId).orElseThrow();
        if (!api.getPortfolio().getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가");
        }

        PortfolioApiModule module = PortfolioApiModule.builder()
                .method(request.getMethod())
                .url(request.getUrl())
                .api(api)
                .description(request.getDescription())
                .response(request.getResponse())
                .build();

        api.addModule(module);
        return module.getId();
    }
    
    @Transactional
    public void updatePortfolioApiModule(Long userId, Long apiId, Long moduleId, PortfolioApiModuleRequest request) {
        PortfolioApi portfolioApi = apiRepository.findById(apiId).orElseThrow();
        if (!portfolioApi.getPortfolio().getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가");
        }

        PortfolioApiModule module = portfolioApi.getModules().stream()
                .filter(m -> m.getId().equals(moduleId))
                .findFirst()
                .orElseThrow();

        module.updatePortfolioApiModule(
                request.getMethod(),
                request.getUrl(),
                request.getDescription(),
                request.getResponse());
    }

    @Transactional
    public void deletePortfolioApiModule(Long userId, Long apiId, Long moduleId) {
        PortfolioApi portfolioApi = apiRepository.findById(apiId).orElseThrow();
        if (!portfolioApi.getPortfolio().getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가");
        }
        PortfolioApiModule module = moduleRepository.findById(moduleId).orElseThrow();
        portfolioApi.removeModule(module);
    }

}
