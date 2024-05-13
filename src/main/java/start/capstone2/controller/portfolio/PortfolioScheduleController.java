package start.capstone2.controller.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioScheduleRequest;
import start.capstone2.dto.portfolio.PortfolioScheduleResponse;
import start.capstone2.service.portfolio.PortfolioScheduleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioScheduleController {

    private final PortfolioScheduleService scheduleService;

    @PostMapping("/{portfolioId}/schedule")
    public Long createPortfolioSchedule(Long userId, Long portfolioId, PortfolioScheduleRequest request) {
        return scheduleService.createPortfolioSchedule(userId, portfolioId, request);
    }

    @PutMapping("/{portfolioId}/schedule/{scheduleId}")
    public void updatePortfolioSchedule(Long userId, @PathVariable Long portfolioId, @PathVariable Long scheduleId, PortfolioScheduleRequest request) {
        scheduleService.updatePortfolioSchedule(userId, portfolioId, scheduleId, request);
    }

    @DeleteMapping("/{portfolioId}/schedule/{scheduleId}")
    public void deletePortfolioSchedule(Long userId, @PathVariable Long portfolioId, @PathVariable Long scheduleId) {
        scheduleService.deletePortfolioSchedule(userId, portfolioId, scheduleId);
    }

    @GetMapping("/{portfolioId}/schedule")
    public ResponseResult<List<PortfolioScheduleResponse>> findAllPortfolioSchedules(Long userId, @PathVariable Long portfolioId) {
        List<PortfolioScheduleResponse> result = scheduleService.findPortfolioSchedules(userId, portfolioId);
        return new ResponseResult<>(result);
    }

}
