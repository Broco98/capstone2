package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.Image.Image;
import start.capstone2.domain.Image.ImageStore;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioFunction;
import start.capstone2.domain.portfolio.repository.PortfolioFunctionRepository;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.domain.user.User;
import start.capstone2.domain.user.repository.UserRepository;
import start.capstone2.dto.portfolio.PortfolioFunctionRequest;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PortfolioFunctionService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioFunctionRepository functionRepository;
    private final ImageStore imageStore; // 이미지 필요한지 아직 미정

    @Transactional
    public Long createPortfolioFunction(Long userId, Long portfolioId, PortfolioFunctionRequest request) {
        User user = userRepository.findById(userId).orElseThrow();
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();

        Image image = imageStore.saveImage(request.getImage());

        PortfolioFunction function = PortfolioFunction.createPortfolioFunction(
                image,
                request.getExplain()
        );

        portfolio.addFunction(function);
        return function.getId();
    }

    @Transactional
    public void updatePortfolioFunction(Long userId, Long portfolioId, Long functionId, PortfolioFunctionRequest request) {
        PortfolioFunction function = functionRepository.findByIdWithImage(portfolioId);

        // 이미지 삭제
        Image oldImage = function.getImage();
        imageStore.removeImage(oldImage);

        Image newImage = imageStore.saveImage(request.getImage());
        function.update(newImage, request.getExplain());
    }

    @Transactional
    public void deletePortfolioComment(Long userId, Long portfolioId, Long functionId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        PortfolioFunction function = functionRepository.findById(functionId).orElseThrow();
        portfolio.removeFunction(function);
    }
}
