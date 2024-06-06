package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioDesign;
import start.capstone2.domain.portfolio.PortfolioDesignDiagram;
import start.capstone2.domain.portfolio.ShareStatus;
import start.capstone2.domain.portfolio.repository.PortfolioDesignRepository;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.domain.user.User;
import start.capstone2.domain.user.repository.UserRepository;
import start.capstone2.dto.portfolio.PortfolioDesignDiagramResponse;
import start.capstone2.dto.portfolio.PortfolioDesignRequest;
import start.capstone2.dto.portfolio.PortfolioDesignResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PortfolioDesignService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioDesignRepository designRepository;
    
    @Transactional
    public Long createPortfolioDesign(Long userId, Long portfolioId, PortfolioDesignRequest request) {

        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        if (!portfolio.getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가");
        }
        PortfolioDesign design = PortfolioDesign.builder()
                .portfolio(portfolio)
                .name(request.getName())
                .build();

        portfolio.addDesign(design);
        return design.getId();
    }
    
    @Transactional
    public void updatePortfolioDesign(Long userId, Long portfolioId, Long designId, PortfolioDesignRequest request) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        if (!portfolio.getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가");
        }
        PortfolioDesign design = portfolio.getDesigns().stream().filter(d->d.getId().equals(designId)).findFirst().orElseThrow();
        design.updatePortfolioDesign(request.getName());
    }
    
    @Transactional
    public void deletePortfolioDesign(Long userId, Long portfolioId, Long designId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        if (!portfolio.getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가");
        }
        PortfolioDesign design = portfolio.getDesigns().stream().filter(d->d.getId().equals(designId)).findFirst().orElseThrow();
        portfolio.removeDesign(design);
    }

    // 해당 포트폴리오의 모든 디자인 조회
    public List<PortfolioDesignResponse> findPortfolioDesigns(Long userId, Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();

        if (!portfolio.getUser().getId().equals(userId) && portfolio.getStatus().equals(ShareStatus.NOT_SHARED)) {
            throw new IllegalStateException("접근 불가");
        }

        List<PortfolioDesign> designs = portfolio.getDesigns();
        List<PortfolioDesignResponse> results = new ArrayList<>();

        for (PortfolioDesign design : designs) {
            List<PortfolioDesignDiagram> diagrams = design.getDiagrams();
            List<PortfolioDesignDiagramResponse> diagramResponses = new ArrayList<>();

            for (PortfolioDesignDiagram d : diagrams) {
                String imageName = "default.png";
                if (d.getImage() != null) {
                    imageName = d.getImage().getSavedName();
                }
                diagramResponses.add(new PortfolioDesignDiagramResponse(d.getId(), d.getDiagram(), d.getDiagram(), imageName));
            }

            results.add(new PortfolioDesignResponse(design.getId(), design.getName(), diagramResponses));
        }
        return results;
    }

}
