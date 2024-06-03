package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import start.capstone2.domain.BaseEntity;
import start.capstone2.domain.techstack.TechStack;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PortfolioTechStack extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_techstack_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "techstack_id")
    private TechStack techStack;

    @Builder
    private PortfolioTechStack(Portfolio portfolio, TechStack techStack) {
        this.portfolio = portfolio;
        this.techStack = techStack;
    }
}
