package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioFunction;
import start.capstone2.domain.portfolio.PortfolioFunctionModule;
import start.capstone2.domain.portfolio.repository.PortfolioFunctionModuleRepository;
import start.capstone2.domain.portfolio.repository.PortfolioFunctionRepository;
import start.capstone2.domain.user.User;
import start.capstone2.domain.user.repository.UserRepository;
import start.capstone2.dto.portfolio.PortfolioFunctionModuleRequest;
import start.capstone2.dto.portfolio.PortfolioFunctionModuleResponse;
import start.capstone2.dto.portfolio.PortfolioFunctionRequest;
import start.capstone2.dto.portfolio.PortfolioFunctionResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PortfolioFunctionModuleService {

    private final UserRepository userRepository;
    private final PortfolioFunctionRepository functionRepository;
    private final PortfolioFunctionModuleRepository moduleRepository;

    public Long createPortfolioFunctionModule(Long userId, PortfolioFunction function, PortfolioFunctionModuleRequest request) {
        User user = userRepository.findById(userId).orElseThrow();
        PortfolioFunctionModule functionModule = PortfolioFunctionModule.builder()
                .function(function)
                .name(request.getName())
                .description(request.getDescription())
                .build();

        function.addModule(functionModule);
        return functionModule.getId();
    }


    // TODO user 확인 필요
    @Transactional
    public Long createPortfolioFunctionModule(Long userId, Long functionId, PortfolioFunctionModuleRequest request) {
        User user = userRepository.findById(userId).orElseThrow();
        PortfolioFunction function = functionRepository.findById(functionId).orElseThrow();

        PortfolioFunctionModule functionModule = PortfolioFunctionModule.builder()
                .function(function)
                .name(request.getName())
                .description(request.getDescription())
                .build();

        function.addModule(functionModule);
        return functionModule.getId();
    }

    // TODO user 확인 필요
    @Transactional
    public void updatePortfolioFunctionModule(Long userId, Long moduleId, PortfolioFunctionModuleRequest request) {
        PortfolioFunctionModule module = moduleRepository.findById(moduleId).orElseThrow();
        module.updateFunctionModule(request.getName(), request.getDescription());
    }

    // TODO user 확인 필요
    @Transactional
    public void deletePortfolioFunction(Long userId, Long functionId, Long moduleId) {
        PortfolioFunction function = functionRepository.findById(functionId).orElseThrow();
        PortfolioFunctionModule module = moduleRepository.findById(moduleId).orElseThrow();
        function.removeModule(module);
    }

    // TODO user 확인 필요
    // function 모든 module 조회
    public List<PortfolioFunctionModuleResponse> findPortfolioFunctionModule(Long userId, Long functionId) {
        List<PortfolioFunctionModule> modules = moduleRepository.findAllByFunction_Id(functionId);
        List<PortfolioFunctionModuleResponse> results = new ArrayList<>();
        for (PortfolioFunctionModule module : modules) {
            results.add(new PortfolioFunctionModuleResponse(module.getId(), module.getName(), module.getDescription()));
        }
        return results;
    }

}
