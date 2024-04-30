package start.capstone2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import start.capstone2.ResponseResult;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioGroup;
import start.capstone2.domain.portfolio.dto.PortfolioRequest;
import start.capstone2.domain.user.User;
import start.capstone2.domain.user.dto.UserRequest;
import start.capstone2.service.PortfolioService;
import start.capstone2.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    @PostMapping("/create")
    public ResponseEntity<Long> createPortfolio(Long userId, PortfolioRequest portfolioRequest) {
        Long portfolioId = portfolioService.createPortfolio(userId, portfolioRequest);
        Portfolio portfolio = portfolioService.findById(portfolioId);

        log.info("portfolioRequest ={}", portfolioRequest);

        return ResponseEntity.ok(portfolioId);
    }

}
