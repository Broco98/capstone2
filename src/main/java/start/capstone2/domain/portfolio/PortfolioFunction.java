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
public class PortfolioFunction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_function_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    @Setter
    private Portfolio portfolio;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @Lob
    private String explain;

    @Embedded
    private BaseEntity baseEntity;


    public static PortfolioFunction createPortfolioFunction(Image image, String explain) {
        PortfolioFunction portfolioFunction = new PortfolioFunction();
        portfolioFunction.image = image;
        portfolioFunction.explain = explain;
        return portfolioFunction;
    }

    public void update(Image image, String explain) {
        this.image = image;
        this.explain = explain;
    }

    public void remove() {
        image.remove();
    }
}
