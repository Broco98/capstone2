//package start.capstone2.service.portfolio;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import start.capstone2.domain.portfolio.Portfolio;
//import start.capstone2.domain.portfolio.PortfolioInterview;
//import start.capstone2.domain.portfolio.repository.PortfolioInterviewRepository;
//import start.capstone2.domain.portfolio.repository.PortfolioRepository;
//import start.capstone2.domain.user.repository.UserRepository;
//import start.capstone2.dto.portfolio.PortfolioInterviewRequest;
//import start.capstone2.dto.portfolio.PortfolioInterviewResponse;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//public class PortfolioInterviewService {
//
//    private final PortfolioInterviewRepository interviewRepository;
//    private final PortfolioRepository portfolioRepository;
//    private final UserRepository userRepository;
//
//    // TODO user 정보 필요
//    @Transactional
//    public Long createPortfolioInterview(Long userId, Long portfolioId, PortfolioInterviewRequest request) {
//        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
//
//        PortfolioInterview interview = PortfolioInterview.builder()
//                .portfolio(portfolio)
//                .question(request.getQuestion())
//                .answer(request.getAnswer())
//                .build();
//
//        portfolio.addInterview(interview);
//        return interview.getId();
//    }
//
//
//    // TODO user 정보 필요
//    @Transactional
//    public void updatePortfolioInterview(Long userId, Long interviewId, PortfolioInterviewRequest request) {
//        PortfolioInterview interview = interviewRepository.findById(interviewId).orElseThrow();
//        interview.updatePortfolioInterview(request.getQuestion(), request.getAnswer());
//    }
//
//    // TODO user 정보 필요
//    @Transactional
//    public void deletePortfolioInterview(Long userId, Long portfolioId, Long interviewId) {
//        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
//        PortfolioInterview interview = interviewRepository.findById(interviewId).orElseThrow();
//        portfolio.removeInterview(interview);
//    }
//
//    // TODO user 정보 필요
//    // 해당 포트폴리오의 모든 interview 조회
//    public List<PortfolioInterviewResponse> findPortfolioInterview(Long userId, Long portfolioId) {
//        List<PortfolioInterview> interviews = interviewRepository.findAllByPortfolioId(portfolioId);
//        List<PortfolioInterviewResponse> results = new ArrayList<>();
//        for (PortfolioInterview interview : interviews) {
//            results.add(new PortfolioInterviewResponse(interview.getId(), interview.getQuestion(), interview.getAnswer()));
//        }
//        return results;
//    }
//
//}
