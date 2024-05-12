package start.capstone2.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import start.capstone2.domain.portfolio.PortfolioGroup;
import start.capstone2.domain.portfolio.dto.PortfolioDto;
import start.capstone2.dto.UserRequest;
import start.capstone2.service.portfolio.PortfolioService;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class PortfolioServiceTest {

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @BeforeEach
    void beforeEach() {
        userService.createUser(new UserRequest("id1", "pw1", "test1"));
        userService.createUser(new UserRequest("id2", "pw2", "test2"));
        userService.createUser(new UserRequest("id3", "pw3", "test3"));

        groupService.createGroup("group1");
        groupService.createGroup("all");
    }

    @Test
    @DisplayName("포트폴리오 생성, 조회")
    void createPortfolio() {

        List<PortfolioGroup> portfolioGroups = new ArrayList<>();
        portfolioGroups.add(PortfolioGroup.createPortfolioGroup(groupService.findById(1L)));
        portfolioGroups.add(PortfolioGroup.createPortfolioGroup(groupService.findById(2L)));

        List<PortfolioGroup> portfolioGroups2 = new ArrayList<>();
        portfolioGroups2.add(PortfolioGroup.createPortfolioGroup(groupService.findById(1L)));
        portfolioGroups2.add(PortfolioGroup.createPortfolioGroup(groupService.findById(2L)));

//        portfolioService.createPortfolio(1L, new
//                PortfolioDto(
//                "title1",
//                LocalDateTime.now(),
//                LocalDateTime.now(),
//                10,
//                "목적1이다 시키야",
//                "자세한 내용은 이렇습니다 ㅎ",
//                portfolioGroups)
//        );
//
//        portfolioService.createPortfolio(1L, new
//                PortfolioDto(
//                "title2",
//                LocalDateTime.now(),
//                LocalDateTime.now(),
//                10,
//                "목적1이다 시키야",
//                "자세한 내용은 이렇습니다 ㅎ",
//                portfolioGroups2)
//        );

    }

}