package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import start.capstone2.domain.techstack.TechStack;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PortfolioTechStack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_techstack_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "techstack_id")
    private TechStack techStack;


    public static PortfolioTechStack createPortfolioTechStack(Portfolio portfolio, TechStack techStack) {
        PortfolioTechStack portfolioTechStack = new PortfolioTechStack();
        portfolioTechStack.portfolio = portfolio;
        portfolioTechStack.techStack = techStack;

        return portfolioTechStack;
    }

}
