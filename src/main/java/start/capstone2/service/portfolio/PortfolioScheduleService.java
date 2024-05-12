package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioComment;
import start.capstone2.domain.portfolio.PortfolioSchedule;
import start.capstone2.domain.portfolio.repository.PortfolioCommentRepository;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.domain.portfolio.repository.PortfolioScheduleRepository;
import start.capstone2.domain.user.User;
import start.capstone2.domain.user.repository.UserRepository;
import start.capstone2.dto.portfolio.PortfolioCommentRequest;
import start.capstone2.dto.portfolio.PortfolioScheduleRequest;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PortfolioScheduleService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioScheduleRepository scheduleRepository;

    @Transactional
    public Long createPortfolioSchedule(Long userId, Long portfolioId, PortfolioScheduleRequest request) {
        User user = userRepository.findById(userId).orElseThrow();
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();

        PortfolioSchedule schedule = PortfolioSchedule.createPortfolioSchedule(
                request.getStartDate(),
                request.getEndDate(),
                request.getExplain()
        );

        portfolio.addSchedule(schedule);
        return schedule.getId();
    }

    @Transactional
    public void updatePortfolioSchedule(Long userId, Long portfolioId, Long scheduleId, PortfolioScheduleRequest request) {
        PortfolioSchedule schedule = scheduleRepository.findById(scheduleId).orElseThrow();
        schedule.updatePortfolioSchedule(
                request.getStartDate(),
                request.getEndDate(),
                request.getExplain()
        );
    }


    @Transactional
    public void deletePortfolioSchedule(Long userId, Long portfolioId, Long scheduleId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        PortfolioSchedule schedule = scheduleRepository.findById(scheduleId).orElseThrow();
        portfolio.removeSchedule(schedule);
    }

}
