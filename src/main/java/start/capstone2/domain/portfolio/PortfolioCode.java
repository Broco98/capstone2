package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import start.capstone2.domain.BaseEntity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PortfolioCode extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_code_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @Column(nullable = false)
    @Lob
    private String code;

    @Lob
    private String description;


    public static PortfolioCode createPortfolioCode(Portfolio portfolio, String code, String description) {
        PortfolioCode portfolioCode = new PortfolioCode();
        portfolioCode.portfolio = portfolio;
        portfolioCode.code = code;
        portfolioCode.description = description;

        return portfolioCode;
    }

    public void updatePortfolioCode(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
