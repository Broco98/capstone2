package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.Image.Image;
import start.capstone2.domain.Image.ImageStore;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioDesign;
import start.capstone2.domain.portfolio.repository.PortfolioDesignRepository;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.domain.user.User;
import start.capstone2.domain.user.repository.UserRepository;
import start.capstone2.dto.portfolio.PortfolioDesignRequest;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PortfolioDesignService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioDesignRepository designRepository;
    private final ImageStore imageStore;

    @Transactional
    public Long createPortfolioDesign(Long userId, Long portfolioId, PortfolioDesignRequest request) {
        User user = userRepository.findById(userId).orElseThrow();
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();

        Image image = imageStore.saveImage(request.getImage());

        PortfolioDesign design = PortfolioDesign.createPortfolioDesign(
                image,
                request.getExplain()
        );

        portfolio.addDesign(design);
        return design.getId();
    }

    @Transactional
    public void updatePortfolioDesign(Long userId, Long portfolioId, Long designId, PortfolioDesignRequest request) {
        PortfolioDesign design = designRepository.findByIdWithImage(portfolioId);

        // 이미지 삭제
        Image oldImage = design.getImage();
        imageStore.removeImage(oldImage);

        Image newImage = imageStore.saveImage(request.getImage());
        design.update(newImage, request.getExplain());
    }
    
    @Transactional
    public void deletePortfolioComment(Long userId, Long portfolioId, Long designId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        PortfolioDesign design = designRepository.findById(designId).orElseThrow(); // 삭제하기 위해 2번 조회 -> 1번 조회하기 위해선 또 다른 식별자가 필요함
        portfolio.removeDesign(design);
    }

}
