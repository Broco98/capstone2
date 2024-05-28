package start.capstone2.controller.portfolio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioInterviewRequest;
import start.capstone2.dto.portfolio.PortfolioInterviewResponse;
import start.capstone2.service.portfolio.PortfolioInterviewService;

import java.util.List;

@Tag(name="PortfolioInterview api", description = "포트폴리오 관련 면접 질문 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioInterviewController {

    private final PortfolioInterviewService interviewService;

    @Operation(summary = "create portfolio interview", description = "포트폴리오 인터뷰 단일 생성")
    @PostMapping("/{portfolioId}/interview")
    public Long createPortfolioInterview(Long userId, @PathVariable Long portfolioId, PortfolioInterviewRequest request) {
        return interviewService.createPortfolioInterview(userId, portfolioId, request);
    }

    @Operation(summary = "update portfolio interview", description = "포트폴리오 인터뷰 수정")
    @PutMapping("/{portfolioId}/interview/{interviewId}")
    public void updatePortfolioDesign(Long userId, @PathVariable Long portfolioId, @PathVariable Long interviewId, PortfolioInterviewRequest request) {
        interviewService.updatePortfolioInterview(userId, interviewId, request);
    }

    @Operation(summary = "delete portfolio interviewId", description = "포트폴리오 인터뷰 단일 삭제")
    @DeleteMapping("/{portfolioId}/interview/{interviewId}")
    public void deletePortfolioPortfolioDesign(Long userId, @PathVariable Long portfolioId, @PathVariable Long interviewId) {
        interviewService.deletePortfolioInterview(userId, portfolioId, interviewId);
    }

    @Operation(summary = "find all portfolio interviewId", description = "포트폴리오의 모든 인터뷰 조회")
    @GetMapping("/{portfolioId}/interview")
    public ResponseResult<List<PortfolioInterviewResponse>> findAllPortfolioDesign(Long userId, @PathVariable Long portfolioId) {
        List<PortfolioInterviewResponse> result = interviewService.findPortfolioInterview(userId, portfolioId);
        return new ResponseResult<>(result);
    }

}
