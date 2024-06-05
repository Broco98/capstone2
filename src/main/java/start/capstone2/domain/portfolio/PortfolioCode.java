package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    private String name;

    @Lob
    private String code;

    @Lob
    private String description;

    @Builder
    private PortfolioCode(Portfolio portfolio, String name, String code, String description) {
        this.portfolio = portfolio;
        this.name = name;
        this.code = code;
        this.description = description;
    }

    public void updatePortfolioCode(String name, String code, String description) {
        this.name = name;
        this.code = code;
        this.description = description;
    }
}
