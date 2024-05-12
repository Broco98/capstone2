package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import start.capstone2.domain.Image.Image;
import start.capstone2.domain.Image.ImageStore;
//import start.capstone2.domain.Image.repository.ImageRepository;
import start.capstone2.domain.portfolio.*;
import start.capstone2.dto.portfolio.PortfolioRequest;
import start.capstone2.domain.portfolio.repository.*;
import start.capstone2.domain.user.User;
import start.capstone2.domain.user.repository.GroupRepository;
import start.capstone2.domain.user.repository.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PortfolioService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final ImageStore imageStore;

    @Transactional
    public Long createPortfolio(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Portfolio portfolio = Portfolio.createEmptyPortfolio(user);
        portfolioRepository.save(portfolio);
        return portfolio.getId();
    }
    

    @Transactional
    public void updatePortfolio(Long userId, Long portfolioId, PortfolioRequest portfolioRequest) {
        User user = userRepository.findById(userId).orElseThrow();

        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        Image image = portfolio.getCardImage();
        if (portfolioRequest.getCardImage() != null) {

            if (image != null) {
                imageStore.removeImage(image);
            }
            image = imageStore.saveImage(portfolioRequest.getCardImage());
        }

        portfolio.updatePortfolio(
                portfolioRequest.getTitle(),
                portfolioRequest.getStartDate(),
                portfolioRequest.getEndDate(),
                portfolioRequest.getContribution(),
                portfolioRequest.getPurpose(),
                portfolioRequest.getMemberNum(),
                image,
                portfolioRequest.getStatus()
        );
    }

    @Transactional
    public void deletePortfolio(Long userId, Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        portfolio.deletePortfolio();
        portfolioRepository.delete(portfolio);
    }

    public Portfolio findById(Long portfolioId) {
        return portfolioRepository.findById(portfolioId).orElseThrow();
    }
}
