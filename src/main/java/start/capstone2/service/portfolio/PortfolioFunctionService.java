package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioFunction;
import start.capstone2.domain.portfolio.repository.PortfolioFunctionRepository;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.domain.user.User;
import start.capstone2.domain.user.repository.UserRepository;
import start.capstone2.dto.portfolio.PortfolioFunctionRequest;
import start.capstone2.dto.portfolio.PortfolioFunctionResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PortfolioFunctionService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioFunctionRepository functionRepository;

    @Transactional
    public Long createPortfolioFunction(Long userId, Long portfolioId, PortfolioFunctionRequest request) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();

        PortfolioFunction function = PortfolioFunction.builder()
                .portfolio(portfolio)
                .name(request.getName())
                .build();

        portfolio.addFunction(function);
        return function.getId();
    }

    @Transactional
    public void updatePortfolioFunction(Long userId, Long portfolioId, Long functionId, PortfolioFunctionRequest request) {
        PortfolioFunction function = functionRepository.findById(functionId).orElseThrow();
        function.updatePortfolioFunction(request.getName());
    }

    @Transactional
    public void deletePortfolioFunction(Long userId, Long portfolioId, Long functionId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        PortfolioFunction function = functionRepository.findById(functionId).orElseThrow();
        portfolio.removeFunction(function);
    }

    // TODO user 확인 필요
    // 포트폴리오의 모든 function 조회
    public List<PortfolioFunctionResponse> findPortfolioFunctions(Long userId, Long portfolioId) {
        List<PortfolioFunction> functions = functionRepository.findAllByPortfolioId(portfolioId);
        List<PortfolioFunctionResponse> results = new ArrayList<>();
        for (PortfolioFunction function : functions) {
            results.add(new PortfolioFunctionResponse(function.getId(), function.getName()));
        }
        return results;
    }
    
    // 포트폴리오 function 단일 조회
    public PortfolioFunction findById(Long portfolioFunctionId) {
        return functionRepository.findByIdWithModules(portfolioFunctionId);
    }

    public List<PortfolioFunction> findAllByPortfolioIdWithModule(Long portfolioId) {
        return functionRepository.findAllByPortfolioIdWithModule(portfolioId);
    }

}
