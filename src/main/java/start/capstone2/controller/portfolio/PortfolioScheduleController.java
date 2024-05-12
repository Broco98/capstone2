package start.capstone2.controller.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.portfolio.PortfolioFunctionRequest;
import start.capstone2.dto.portfolio.PortfolioScheduleRequest;
import start.capstone2.service.portfolio.PortfolioFunctionService;
import start.capstone2.service.portfolio.PortfolioScheduleService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio/schedule")
public class PortfolioScheduleController {

    private final PortfolioScheduleService scheduleService;

    @PostMapping("/")
    public Long createFeedback(Long userId, Long portfolioId, PortfolioScheduleRequest request) {
        return scheduleService.createPortfolioSchedule(userId, portfolioId, request);
    }

    @PutMapping("/{scheduleId}")
    public void updateFeedback(Long userId, Long portfolioId, @PathVariable Long scheduleId, PortfolioScheduleRequest request) {
        scheduleService.updatePortfolioSchedule(userId, portfolioId, scheduleId, request);
    }

    @DeleteMapping("/{scheduleId}")
    public void deleteFeedback(Long userId, Long portfolioId, @PathVariable Long scheduleId) {
        scheduleService.deletePortfolioSchedule(userId, portfolioId, scheduleId);
    }

}
