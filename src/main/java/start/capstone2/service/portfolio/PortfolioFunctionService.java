package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.Image.Image;
import start.capstone2.domain.Image.ImageStore;
import start.capstone2.domain.Image.S3Store;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioApi;
import start.capstone2.domain.portfolio.PortfolioFeedback;
import start.capstone2.domain.portfolio.PortfolioFunction;
import start.capstone2.domain.portfolio.repository.PortfolioFunctionRepository;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.domain.user.User;
import start.capstone2.domain.user.repository.UserRepository;
import start.capstone2.dto.portfolio.PortfolioApiResponse;
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
        User user = userRepository.findById(userId).orElseThrow();
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();

        PortfolioFunction function = PortfolioFunction.createPortfolioFunction(
                portfolio,
                request.getDescription()
        );

        portfolio.addFunction(function);
        return function.getId();
    }

    @Transactional
    public void updatePortfolioFunction(Long userId, Long portfolioId, Long functionId, PortfolioFunctionRequest request) {
        PortfolioFunction function = functionRepository.findByIdWithImage(portfolioId);

        function.updatePortfolioFunction(request.getDescription());
    }

    @Transactional
    public void deletePortfolioFunction(Long userId, Long portfolioId, Long functionId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        PortfolioFunction function = functionRepository.findById(functionId).orElseThrow();
        portfolio.removeFunction(function);
    }

    public List<PortfolioFunctionResponse> findPortfolioFunctions(Long userId, Long portfolioId) {
        List<PortfolioFunction> functions = functionRepository.findAllByPortfolioId(portfolioId);
        List<PortfolioFunctionResponse> results = new ArrayList<>();
        for (PortfolioFunction function : functions) {
            results.add(new PortfolioFunctionResponse(function.getId(), function.getDescription()));
        }
        return results;
    }

}
