package start.capstone2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import start.capstone2.dto.portfolio.PortfolioResponse;
import start.capstone2.service.UserService;
import start.capstone2.service.portfolio.PortfolioService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final PortfolioService portfolioService;

//    @GetMapping("/portfolios")
//    public ResponseEntity<List<PortfolioResponse>> findAllMyPortfolio() {

        // TODO
//    }


}
