package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioTechStack;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.domain.portfolio.repository.PortfolioTechStackRepository;
import start.capstone2.domain.techstack.TechStack;
import start.capstone2.domain.techstack.repository.TechStackRepository;
import start.capstone2.domain.user.repository.UserRepository;
import start.capstone2.dto.portfolio.PortfolioRequest;
import start.capstone2.dto.portfolio.PortfolioTechStackRequest;
import start.capstone2.dto.portfolio.PortfolioTechStackResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortfolioTechStackService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final TechStackRepository techStackRepository;
    private final PortfolioTechStackRepository portfolioTechStackRepository;

    @Transactional
    public Long createPortfolioTechStack(Long userId, Long portfolioId, PortfolioTechStackRequest request) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        TechStack techStack = techStackRepository.findById(request.getTechStackId()).orElseThrow();
        PortfolioTechStack portfolioTechStack = PortfolioTechStack.createPortfolioTechStack(portfolio, techStack);
        portfolio.addTechStack(portfolioTechStack);
        return portfolioTechStack.getId();
    }

    @Transactional
    public void deletePortfolioTechStack(Long userId, Long portfolioId, Long techStackId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        PortfolioTechStack techStack = portfolioTechStackRepository.findById(techStackId).orElseThrow();

        portfolio.removeTechStack(techStack);
    }

    public List<PortfolioTechStackResponse> findAllPortfolioTechStack(Long userId, Long portfolioId) {
        List<PortfolioTechStack> techStacks = portfolioTechStackRepository.findAllByPortfolioId(portfolioId);
        List<PortfolioTechStackResponse> results = new ArrayList<>();
        for (PortfolioTechStack techStack : techStacks) {
            TechStack stack = techStack.getTechStack();
            results.add(new PortfolioTechStackResponse(stack.getId(), stack.getName(), stack.getImage().getSavedName()));
        }
        return results;
    }
}
