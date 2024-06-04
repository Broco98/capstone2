//package start.capstone2.service.portfolio;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import start.capstone2.domain.portfolio.Portfolio;
//import start.capstone2.domain.portfolio.PortfolioTechStack;
//import start.capstone2.domain.portfolio.repository.PortfolioRepository;
//import start.capstone2.domain.user.repository.UserRepository;
//import start.capstone2.dto.portfolio.PortfolioTechStackRequest;
//
//@Service
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//public class PortfolioTechStackService {
//
//    private final UserRepository userRepository;
//    private final PortfolioRepository portfolioRepository;
//
//    @Transactional
//    public void createPortfolioTechStack(Long userId, Long portfolioId, PortfolioTechStackRequest request) {
//        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
//
//        if (request.getTechStacks().isEmpty()) {
//            return;
//        }
//
//        for (String str : request.getTechStacks()) {
//            PortfolioTechStack techStack = PortfolioTechStack.builder()
//                    .portfolio(portfolio)
//                    .techStack(str)
//                    .build();
//            portfolio.addTechStack(techStack);
//        }
//    }
//
//    @Transactional
//    public void deletePortfolioTechStack(Long userId, Long portfolioId, Long techStackId) {
//        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
//        PortfolioTechStack techStack = portfolio.getTechStacks().stream().filter(t->t.getId().equals(techStackId)).findFirst().orElseThrow();
//        portfolio.removeTechStack(techStack);
//    }
//
//    // 포트폴리오의 모든 techstack 조회
////    public List<PortfolioTechStackResponse> findAllPortfolioTechStack(Long userId, Long portfolioId) {
////        List<PortfolioTechStack> techStacks = portfolioTechStackRepository.findAllByPortfolioId(portfolioId);
////        List<PortfolioTechStackResponse> results = new ArrayList<>();
////        for (PortfolioTechStack techStack : techStacks) {
////            TechStack stack = techStack.getTechStack();
////            results.add(new PortfolioTechStackResponse(stack.getId(), stack.getName(), stack.getImage().getSavedName()));
////        }
////        return results;
////    }
//}
