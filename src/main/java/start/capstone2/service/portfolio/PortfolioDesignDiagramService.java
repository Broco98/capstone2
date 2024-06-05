package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.Image.Image;
import start.capstone2.domain.Image.S3Store;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioDesign;
import start.capstone2.domain.portfolio.PortfolioDesignDiagram;
import start.capstone2.domain.portfolio.repository.PortfolioDesignDiagramRepository;
import start.capstone2.domain.portfolio.repository.PortfolioDesignRepository;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.domain.user.User;
import start.capstone2.domain.user.repository.UserRepository;
import start.capstone2.dto.portfolio.PortfolioDesignDiagramRequest;
import start.capstone2.dto.portfolio.PortfolioDesignRequest;
import start.capstone2.dto.portfolio.PortfolioDesignResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PortfolioDesignDiagramService {

    private final UserRepository userRepository;
    private final PortfolioDesignRepository designRepository;
    private final PortfolioDesignDiagramRepository diagramRepository;
    private final S3Store store;

    @Transactional
    public Long createPortfolioDesignDiagram(Long userId, Long designId, PortfolioDesignDiagramRequest request) {

        PortfolioDesign design = designRepository.findById(designId).orElseThrow();

        Image image = null;
        if (request.getImage() != null) {
            image = store.saveImage(request.getImage());
        }

        PortfolioDesignDiagram diagram = PortfolioDesignDiagram.builder()
                .image(image)
                .diagram(request.getDiagram())
                .description(request.getDescription())
                .build();

        design.addDiagram(diagram);
        return diagram.getId();
    }
    
    @Transactional
    public void updatePortfolioDesignSchema(Long userId, Long designId, Long diagramId, PortfolioDesignDiagramRequest request) {
        PortfolioDesign design = designRepository.findById(designId).orElseThrow();
        PortfolioDesignDiagram diagram = design.getDiagrams().stream()
                .filter(d -> d.getId().equals(diagramId))
                .findFirst().orElseThrow();

        Image image = null;
        if (request.getImage() != null) {
            image = store.saveImage(request.getImage());
        }

        if (diagram.getImage() != null) {
            store.removeImage(diagram.getImage().getSavedName());
        }

        diagram.updatePortfolioDesignDiagram(request.getDiagram(), image, request.getDescription());
    }
    
    @Transactional
    public void deletePortfolioDesignDiagram(Long userId, Long designId, Long diagramId) {
        PortfolioDesign design = designRepository.findById(designId).orElseThrow();
        PortfolioDesignDiagram diagram = design.getDiagrams().stream().filter(d->d.getId().equals(diagramId)).findFirst().orElseThrow();
        if (diagram.getImage() != null) {
            store.removeImage(diagram.getImage().getSavedName());
        }
        design.removeDiagram(diagram);
    }


    //    // 해당 포트폴리오의 모든 디자인 조회
//    public List<PortfolioDesignResponse> findPortfolioDesigns(Long userId, Long portfolioId) {
//        List<PortfolioDesign> designs = designRepository.findAllByPortfolioId(portfolioId);
//        List<PortfolioDesignResponse> results = new ArrayList<>();
//        for (PortfolioDesign design : designs) {
//            results.add(new PortfolioDesignResponse(design.getId(), design.getDesign(), design.getDescription()));
//        }
//        return results;
//    }

}
