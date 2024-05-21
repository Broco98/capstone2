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
public class PortfolioFunction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_function_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @Lob
    private String description;


    public static PortfolioFunction createPortfolioFunction(Portfolio portfolio, String description) {
        PortfolioFunction function = new PortfolioFunction();
        function.portfolio = portfolio;
        function.description = description;
        return function;
    }

    public void updatePortfolioFunction(String description) {
        this.description = description;
    }

}
