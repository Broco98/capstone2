package start.capstone2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import start.capstone2.domain.Image.Image;
import start.capstone2.domain.Image.repository.ImageRepository;
import start.capstone2.domain.file.ImageStore;
import start.capstone2.domain.portfolio.*;
import start.capstone2.domain.portfolio.dto.PortfolioRequest;
import start.capstone2.domain.portfolio.repository.*;
import start.capstone2.domain.user.User;
import start.capstone2.domain.user.repository.GroupRepository;
import start.capstone2.domain.user.repository.UserRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PortfolioService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final GroupRepository groupRepository;
    private final ImageStore imageStore;
    private final ImageRepository imageRepository;
    private final PortfolioCardImageRepository cardRepository;
    private final PortfolioCommentRepository commentRepository;
    private final PortfolioFeedbackRepository feedbackRepository;
    private final PortfolioPostRepository postRepository;

    @Transactional
    public List<PortfolioGroup> createPortfolioGroups(List<Long> sharedGroupIds) {

        if (sharedGroupIds == null || sharedGroupIds.isEmpty()) {
            return null;
        }

        List<PortfolioGroup> groups = new ArrayList<>();
        for (Long id : sharedGroupIds) {
            groups.add(PortfolioGroup.createPortfolioGroup(groupRepository.findById(id).orElseThrow()));
        }

        return groups;
    }

    @Transactional
    public List<PortfolioImage> createPortfolioImages(List<MultipartFile> multipartFiles) throws IOException {

        if (multipartFiles == null || multipartFiles.isEmpty()) {
            return null;
        }

        List<Image> images = imageStore.saveImages(multipartFiles);
        List<PortfolioImage> portfolioImages = new ArrayList<>();
//        imageRepository.saveAll(images);
//        imageRepository.flush();
        for (Image image : images) {
            portfolioImages.add(PortfolioImage.createPortfolioImage(image));
        }

        return portfolioImages;
    }

    @Transactional
    public PortfolioCardImage createCardImage(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }

        Image image = imageStore.saveImage(multipartFile);
//        imageRepository.save(image);
//        imageRepository.flush();
        return PortfolioCardImage.createPortfolioCardImage(image);
    }

    @Transactional
    public Long createPortfolio(Long userId, PortfolioRequest portfolioRequest) throws IOException {
        User user = userRepository.findById(userId).orElseThrow();
        List<PortfolioGroup> groups = createPortfolioGroups(portfolioRequest.getSharedGroupIds());
        List<PortfolioImage> images = createPortfolioImages(portfolioRequest.getImages());
        PortfolioCardImage cardImage = createCardImage(portfolioRequest.getCardImage());

        Portfolio portfolio = Portfolio.createPortfolio(
                user,
                portfolioRequest.getTitle(),
                portfolioRequest.getStartDate(),
                portfolioRequest.getEndDate(),
                portfolioRequest.getContribution(),
                portfolioRequest.getPurpose(),
                portfolioRequest.getContent(),
                cardImage,
                images,
                groups,
                portfolioRequest.getMemberNum());

        portfolioRepository.save(portfolio);
        return portfolio.getId();
    }
    
    // TODO 아직 완벽하진 않음
    @Transactional
    public void updatePortfolio(Long userId, Long portfolioId, PortfolioRequest portfolioRequest) throws IOException {
        User user = userRepository.findById(userId).orElseThrow();

        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();

        List<PortfolioGroup> groups = createPortfolioGroups(portfolioRequest.getSharedGroupIds());
        List<PortfolioImage> images = createPortfolioImages(portfolioRequest.getImages());
        PortfolioCardImage cardImage = createCardImage(portfolioRequest.getCardImage());

        portfolio.updatePortfolio(
                portfolioRequest.getTitle(),
                portfolioRequest.getStartDate(),
                portfolioRequest.getEndDate(),
                portfolioRequest.getContribution(),
                portfolioRequest.getPurpose(),
                portfolioRequest.getContent(),
                cardImage,
                images,
                groups,
                portfolioRequest.getMemberNum());
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
