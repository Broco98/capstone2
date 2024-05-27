package start.capstone2;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import start.capstone2.domain.portfolio.ShareStatus;
import start.capstone2.dto.UserRequest;
import start.capstone2.dto.portfolio.PortfolioDetailRequest;
import start.capstone2.service.UserService;
import start.capstone2.service.portfolio.PortfolioDetailService;
import start.capstone2.service.portfolio.PortfolioService;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class TestInit {

    private final UserService userService;
    private final PortfolioService portfolioService;

    @PostConstruct
    public void init() {
        userService.createUser(new UserRequest("test1", "1111", "test1"));
        userService.createUser(new UserRequest("test2", "2222", "test2"));
        userService.createUser(new UserRequest("test3", "3333", "test3"));

//        portfolioService.createPortfolio(1L);
//        portfolioDetailService.updatePortfolioDetail(1L, 1L, new PortfolioDetailRequest(
//                "test",
//                LocalDate.now(),
//                LocalDate.now(),
//                30,
//                "간단한 게시판을 가진 쇼핑몰 프로젝트",
//                "간단한 게시판을 가진 쇼핑몰 프로젝트",
//                3,
//                null,
//                ShareStatus.SHARE)
//        );
    }



}
