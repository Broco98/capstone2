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

    private final PortfolioFunctionRepository functionRepository;
    private final PortfolioFunctionModuleRepository moduleRepository;

    @Transactional
    public Long createPortfolioFunctionModule(Long userId, Long functionId, PortfolioFunctionModuleRequest request) {
        PortfolioFunction function = functionRepository.findById(functionId).orElseThrow();

        if (!function.getPortfolio().getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가!");
        }

        PortfolioFunctionModule functionModule = PortfolioFunctionModule.builder()
                .function(function)
                .name(request.getName())
                .description(request.getDescription())
                .build();

        function.addModule(functionModule);
        moduleRepository.save(functionModule);
        return functionModule.getId();
    }

    @Transactional
    public void updatePortfolioFunctionModule(Long userId, Long functionId, Long moduleId, PortfolioFunctionModuleRequest request) {
        PortfolioFunction function = functionRepository.findById(functionId).orElseThrow();
        if (!function.getPortfolio().getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가!");
        }
        PortfolioFunctionModule module = function.getModules().stream().filter(m->m.getId().equals(moduleId)).findFirst().orElseThrow();
        module.updateFunctionModule(request.getName(), request.getDescription());
    }

    @Transactional
    public void deletePortfolioFunctionModule(Long userId, Long functionId, Long moduleId) {
        PortfolioFunction function = functionRepository.findById(functionId).orElseThrow();
        if (!function.getPortfolio().getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가!");
        }
        PortfolioFunctionModule module = function.getModules().stream().filter(m->m.getId().equals(moduleId)).findFirst().orElseThrow();
        function.removeModule(module);
    }

}
