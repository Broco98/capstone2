package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import start.capstone2.domain.BaseEntity;
import start.capstone2.domain.Image.Image;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PortfolioDesign extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_design_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @Lob
    private String design;

    @Lob
    private String description;


    public static PortfolioDesign createPortfolioDesign(Portfolio portfolio, String design, String description) {
        PortfolioDesign portfolioDesign = new PortfolioDesign();
        portfolioDesign.portfolio = portfolio;
        portfolioDesign.design = design;
        portfolioDesign.description = description;
        return portfolioDesign;
    }

    public void updatePortfolioDesign(String design, String description) {
        this.design = design;
        this.description = description;
    }
}
