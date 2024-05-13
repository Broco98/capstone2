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
public class PortfolioCode {

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
    private String explain;

    @Embedded
    private BaseEntity baseEntity;


    public static PortfolioCode createPortfolioCode(Portfolio portfolio, String code, String explain) {
        PortfolioCode portfolioCode = new PortfolioCode();
        portfolioCode.portfolio = portfolio;
        portfolioCode.code = code;
        portfolioCode.explain = explain;

        return portfolioCode;
    }

    public void updatePortfolioCode(String code, String explain) {
        this.code = code;
        this.explain = explain;
    }
}
