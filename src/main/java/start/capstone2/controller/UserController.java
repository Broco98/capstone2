package start.capstone2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioResponse;
import start.capstone2.service.UserService;
import start.capstone2.service.portfolio.PortfolioClippingService;
import start.capstone2.service.portfolio.PortfolioLikeService;
import start.capstone2.service.portfolio.PortfolioService;

import java.util.List;

@Tag(name = "User Api", description = "User 관련 조회 Api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final PortfolioService portfolioService;
    private final PortfolioClippingService clippingService;
    private final PortfolioLikeService likeService;

    @Operation(summary = "find all user's portfolio", description = "유저의 모든 포트폴리오를 조회합니다.")
    @GetMapping("")
    public ResponseResult<List<PortfolioResponse>> findAllPortfolio(HttpServletRequest servletRequest) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        return new ResponseResult<>(portfolioService.findAllByUserId(userId));
    }

    @Operation(summary = "find all clipping portfolio", description = "유저가 스크랩한 모든 포트폴리오를 조회합니다.")
    @GetMapping("/clipping")
    public ResponseResult<List<PortfolioResponse>> findAllClippingPortfolio(HttpServletRequest servletRequest) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        return new ResponseResult<>(clippingService.findAllClippingPortfolio(userId));
    }

    @Operation(summary = "find all like portfolio", description = "유저가 좋아요한 포트폴리오를 조회합니다.")
    @GetMapping("/like")
    public ResponseResult<List<PortfolioResponse>> findAllLikePortfolio(HttpServletRequest servletRequest) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        return new ResponseResult<>(likeService.findAllLikePortfolio(userId));
    }
}
