package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import start.capstone2.domain.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PortfolioPpt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_ppt_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    private String savedName;

    @Embedded
    private BaseEntity baseEntity;


    public static PortfolioPpt createPortfolioPpt(Portfolio portfolio, String savedName) {
        PortfolioPpt ppt = new PortfolioPpt();
        ppt.portfolio = portfolio;
        ppt.savedName = savedName;
        return ppt;
    }
}
