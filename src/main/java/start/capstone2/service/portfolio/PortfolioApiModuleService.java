package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.apache.el.stream.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioApi;
import start.capstone2.domain.portfolio.PortfolioApiModule;
import start.capstone2.domain.portfolio.repository.PortfolioApiModuleRepository;
import start.capstone2.domain.portfolio.repository.PortfolioApiRepository;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.domain.user.repository.UserRepository;
import start.capstone2.dto.portfolio.PortfolioApiModuleRequest;
import start.capstone2.dto.portfolio.PortfolioApiModuleResponse;
import start.capstone2.dto.portfolio.PortfolioApiResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PortfolioApiModuleService {

    private final PortfolioApiRepository apiRepository;
    private final PortfolioApiModuleRepository moduleRepository;

    // TODO user 확인 필요
    @Transactional
    public PortfolioApiModule createPortfolioApiModule(Long userId, PortfolioApi portfolioApi, PortfolioApiModuleRequest request) {

        PortfolioApiModule module = PortfolioApiModule.builder()
                .method(request.getMethod())
                .url(request.getUrl())
                .api(portfolioApi)
                .description(request.getDescription())
                .response(request.getResponse())
                .build();

        portfolioApi.addModule(module);

        return module;
    }
    
    // TODO user 확인 필요
    @Transactional
    public void updatePortfolioApiModule(Long userId, Long apiId, Long moduleId, PortfolioApiModuleRequest request) {
        PortfolioApi portfolioApi = apiRepository.findById(apiId).orElseThrow();

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

    // TODO user 확인 필요
    @Transactional
    public void deletePortfolioApiModule(Long userId, Long apiId, Long moduleId) {
        PortfolioApi portfolioApi = apiRepository.findById(apiId).orElseThrow();
        PortfolioApiModule module = moduleRepository.findById(moduleId).orElseThrow();
        portfolioApi.removeModule(module);
    }

    public List<PortfolioApiModuleResponse> findAllPortfolioApiModule(Long userId, Long apiId) {
        PortfolioApi portfolioApi = apiRepository.findById(apiId).orElseThrow();
        return portfolioApi.getModules().stream()
                .map(m -> PortfolioApiModuleResponse.builder()
                        .id(m.getId())
                        .url(m.getUrl())
                        .method(m.getMethod())
                        .description(m.getDescription())
                        .build())
                .collect(Collectors.toList());
    }


}
