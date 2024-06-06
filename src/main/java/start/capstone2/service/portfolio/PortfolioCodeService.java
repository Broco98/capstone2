package start.capstone2.service.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.portfolio.Portfolio;
import start.capstone2.domain.portfolio.PortfolioApi;
import start.capstone2.domain.portfolio.PortfolioCode;
import start.capstone2.domain.portfolio.ShareStatus;
import start.capstone2.domain.portfolio.repository.PortfolioCodeRepository;
import start.capstone2.domain.portfolio.repository.PortfolioRepository;
import start.capstone2.domain.user.repository.UserRepository;
import start.capstone2.dto.portfolio.PortfolioApiResponse;
import start.capstone2.dto.portfolio.PortfolioCodeRequest;
import start.capstone2.dto.portfolio.PortfolioCodeResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PortfolioCodeService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioCodeRepository codeRepository;

    @Transactional
    public Long createPortfolioCode(Long userId, Long portfolioId, PortfolioCodeRequest request) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        if (!portfolio.getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가");
        }
        PortfolioCode code = PortfolioCode.builder()
                .portfolio(portfolio)
                .code(request.getCode())
                .description(request.getDescription())
                .build();

        portfolio.addCode(code);
        return code.getId();
    }

    @Transactional
    public void updatePortfolioCode(Long userId, Long portfolioId, Long codeId, PortfolioCodeRequest request) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        if (!portfolio.getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가");
        }
        PortfolioCode code = portfolio.getCodes().stream().filter(c->c.getId().equals(codeId)).findFirst().orElseThrow();
        code.updatePortfolioCode(request.getName(), request.getCode(), request.getDescription());
    }

    @Transactional
    public void deletePortfolioCode(Long userId, Long portfolioId, Long codeId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        if (!portfolio.getUser().getId().equals(userId)) {
            throw new IllegalStateException("접근 불가");
        }
        PortfolioCode code = portfolio.getCodes().stream().filter(c->c.getId().equals(codeId)).findFirst().orElseThrow();
        portfolio.removeCode(code);
    }

    public List<PortfolioCodeResponse> findPortfolioCodes(Long userId, Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        if (!portfolio.getUser().getId().equals(userId) && portfolio.getStatus().equals(ShareStatus.NOT_SHARED)) {
            throw new IllegalStateException("접근 불가");
        }
        List<PortfolioCode> codes = portfolio.getCodes();
        List<PortfolioCodeResponse> results = new ArrayList<>();
        for (PortfolioCode code : codes) {
            results.add(new PortfolioCodeResponse(code.getId(), code.getName(), code.getCode(), code.getDescription()));
        }
        return results;
    }
}
