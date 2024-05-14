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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @Lob
    private String explain;


    public static PortfolioFunction createPortfolioFunction(Portfolio portfolio, Image image, String explain) {
        PortfolioFunction function = new PortfolioFunction();
        function.portfolio = portfolio;
        function.image = image;
        function.explain = explain;
        return function;
    }

    public void updatePortfolioFunction(Image image, String explain) {
        this.image = image;
        this.explain = explain;
    }

    public void deletePortfolioFunction() {
        image.remove();
    }
}
