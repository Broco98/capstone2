//package start.capstone2.domain.portfolio;
//
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import start.capstone2.domain.BaseEntity;
//
//@Entity
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class PortfolioTechStack extends BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "portfolio_techstack_id")
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "portfolio_id")
//    private Portfolio portfolio;
//
//    private String techStack;
//
//    @Builder
//    private PortfolioTechStack(Portfolio portfolio, String techStack) {
//        this.portfolio = portfolio;
//        this.techStack = techStack;
//    }
//}
