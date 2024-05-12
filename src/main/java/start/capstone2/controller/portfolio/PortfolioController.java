package start.capstone2.controller.portfolio;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import start.capstone2.ResponseResult;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioGroup;
import start.capstone2.domain.portfolio.PortfolioImage;
import start.capstone2.dto.portfolio.PortfolioRequest;
import start.capstone2.dto.portfolio.PortfolioResponse;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.service.portfolio.PortfolioService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final PortfolioRepository portfolioRepository;

    @PostMapping("/")
    public ResponseEntity<Long> createPortfolio(Long userId, PortfolioRequest portfolioRequest) throws IOException {
        Long portfolioId = portfolioService.createPortfolio(userId, portfolioRequest);

        return ResponseEntity.ok(portfolioId);
    }

    @GetMapping("/{portfolioId}")
    public ResponseResult<PortfolioResponse> getSinglePortfolio(Long userId, @PathVariable Long portfolioId) {

//        Portfolio portfolio = portfolioService.findByUserIdAndPortfolioIdWithImages(userId, portfolioId);
        Portfolio portfolio = portfolioService.findById(portfolioId);

        String cardImageName = portfolio.getCardImage().getImage().getSavedName();
        List<String> imageNames = new ArrayList<>();
        for (PortfolioImage image : portfolio.getImages()) {
            imageNames.add(image.getImage().getSavedName());
        }

        List<String> sharedGroupNames = new ArrayList<>();
        for (PortfolioGroup group : portfolio.getSharedGroups()) {
            sharedGroupNames.add(group.getGroup().getName());
        }

        PortfolioResponse portfolioResponse = new PortfolioResponse(
                portfolio.getTitle(),
                portfolio.getStartDate(),
                portfolio.getEndDate(),
                portfolio.getContribution(),
                portfolio.getPurpose(),
                portfolio.getContent(),
                portfolio.getMemberNum(),
                cardImageName,
                imageNames,
                sharedGroupNames
        );

        return new ResponseResult<>(portfolioResponse);
    }

    // TODO
    @PutMapping("/{portfolioId}")
    public void updatePortfolio(Long userId, PortfolioRequest portfolioRequest, @PathVariable Long portfolioId) {

        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();

//        portfolio.updatePortfolio(
//                portfolioRequest.getTitle(),
//                portfolioRequest.getStartDate(),
//                portfolioRequest.getEndDate(),
//                portfolioRequest.getContribution(),
//                portfolioRequest.getPurpose(),
//                portfolioRequest.getContent(),
//                portfolioRequest.getCardImage(),
//                portfolioRequest.getImages(),
//
//        );
    }

}
