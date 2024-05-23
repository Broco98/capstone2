package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioDetail;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.domain.user.repository.UserRepository;
import start.capstone2.dto.portfolio.PortfolioDetailRequest;
import start.capstone2.dto.portfolio.PortfolioDetailResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PortfolioDetailService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;

    @Transactional
    public Long createPortfolioDetail(Long userId, Long portfolioId, PortfolioDetailRequest request) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        PortfolioDetail detail = PortfolioDetail.createPortfolioDetail(request.getStartDate(), request.getEndDate(), request.getTeamNum(), request.getTitle(), request.getDescription(), request.getContribution());
        portfolio.setDetail(detail);
        return detail.getId();
    }

    @Transactional
    public void updatePortfolioDetail(Long userId, Long portfolioId, PortfolioDetailRequest request) {
        Portfolio portfolio = portfolioRepository.findByIdWithDetail(portfolioId);
        portfolio.getDetail().updatePortfolioDetail(
                request.getStartDate(),
                request.getEndDate(),
                request.getTeamNum(),
                request.getTitle(),
                request.getDescription(),
                request.getContribution()
        );
    }

    public PortfolioDetailResponse findPortfolioDetail(Long userId, Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findByIdWithDetail(portfolioId);
        PortfolioDetail detail = portfolio.getDetail();
        return new PortfolioDetailResponse(
                detail.getId(),
                detail.getTitle(),
                detail.getStartDate(),
                detail.getEndDate(),
                detail.getContribution(),
                detail.getDescription(),
                detail.getTeamNum()
        );
    }

}
