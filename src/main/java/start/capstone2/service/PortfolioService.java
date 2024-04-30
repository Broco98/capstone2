package start.capstone2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.Image.Image;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioGroup;
import start.capstone2.domain.portfolio.PortfolioImage;
import start.capstone2.domain.portfolio.dto.PortfolioRequest;
import start.capstone2.domain.portfolio.repository.*;
import start.capstone2.domain.user.User;
import start.capstone2.domain.user.repository.GroupRepository;
import start.capstone2.domain.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PortfolioService {

    // TODO: Member
    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioCardImageRepository cardRepository;
    private final PortfolioCommentRepository commentRepository;
    private final PortfolioFeedbackRepository feedbackRepository;
    private final PortfolioImageRepository imageRepository;
    private final PortfolioPostRepository postRepository;
    private final GroupRepository groupRepository;

    public List<PortfolioImage> createPortfolioImage(List<Image> images) {
        List<PortfolioImage> portfolioImages = new ArrayList<>();

        for (Image image : images) {
            PortfolioImage portfolioImage = PortfolioImage.createPortfolioImage(image);
            portfolioImages.add(portfolioImage);
        }

        return portfolioImages;
    }

    @Transactional
    public Long createPortfolio(Long userId, PortfolioRequest portfolioRequest) {
        User user = userRepository.findById(userId).orElseThrow();

        List<PortfolioGroup> groups = new ArrayList<>();
        System.out.println(portfolioRequest);
        for (Long id: portfolioRequest.getShardGroupIds()) {
            groups.add(PortfolioGroup.createPortfolioGroup(groupRepository.findById(id).orElseThrow()));
        }

        Portfolio portfolio = Portfolio.createPortfolio(
                user,
                portfolioRequest.getTitle(),
                LocalDateTime.parse(portfolioRequest.getStartDate()),
                LocalDateTime.parse(portfolioRequest.getEndDate()),
                portfolioRequest.getContribution(),
                portfolioRequest.getPurpose(),
                portfolioRequest.getContent(),
//                portfolioRequest.getCardImage(),
//                portfolioRequest.getImages(),
                groups
        );

        portfolioRepository.save(portfolio);
        return portfolio.getId();
    }

    @Transactional
    public void updatePortfolio(Long userId, Long portfolioId, PortfolioRequest portfolioRequest) {
        User user = userRepository.findById(userId).orElseThrow();
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();

        List<PortfolioGroup> groups = new ArrayList<>();
        for (Long id: portfolioRequest.getShardGroupIds()) {
            groups.add(PortfolioGroup.createPortfolioGroup(groupRepository.findById(id).orElseThrow()));
        }

        portfolio.update(
                portfolioRequest.getTitle(),
                LocalDateTime.parse(portfolioRequest.getStartDate()),
                LocalDateTime.parse(portfolioRequest.getEndDate()),
                portfolioRequest.getContribution(),
                portfolioRequest.getPurpose(),
                portfolioRequest.getContent(),
//                portfolioRequest.getCardImage(),
//                portfolioRequest.getImages(),
                groups
        );
    }

    @Transactional
    public void deletePortfolio(Long portfolioId) {
        portfolioRepository.deleteById(portfolioId);
    }

    public List<Portfolio> findByUserId(Long userId) {
        return portfolioRepository.findByUserId(userId);
    }

    public List<Portfolio> findPublicPortfolio() {
        return portfolioRepository.findAll();
    }

    public Portfolio findById(Long portfolioId) {
        return portfolioRepository.findById(portfolioId).orElseThrow();
    }

}
