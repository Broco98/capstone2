package start.capstone2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import start.capstone2.ResponseResult;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioGroup;
import start.capstone2.domain.portfolio.PortfolioImage;
import start.capstone2.domain.portfolio.dto.PortfolioRequest;
import start.capstone2.domain.portfolio.dto.PortfolioResponse;
import start.capstone2.service.PortfolioService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    @PostMapping("/")
    public ResponseEntity<Long> createPortfolio(Long userId, PortfolioRequest portfolioRequest) throws IOException {
        Long portfolioId = portfolioService.createPortfolio(userId, portfolioRequest);

        return ResponseEntity.ok(portfolioId);
    }

    @GetMapping("/{portfolioId}")
    public ResponseResult<PortfolioResponse> getSinglePortfolio(Long userId, @PathVariable Long portfolioId) {

        Portfolio portfolio = portfolioService.findByUserIdAndPortfolioIdWithImages(userId, portfolioId);

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



}
