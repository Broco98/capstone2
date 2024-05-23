package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.portfolio.repository.PortfolioInterviewRepository;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortfolioInterviewService {

    private final PortfolioInterviewRepository interviewRepository;
    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;

    public Long

}
