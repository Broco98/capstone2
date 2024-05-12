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
public class PortfolioDesign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_design_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @Setter
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;

    @Lob
    private String explain;

    @Embedded
    private BaseEntity baseEntity;


    public static PortfolioDesign createPortfolioDesign(Image image, String explain) {
        PortfolioDesign portfolioDesign = new PortfolioDesign();
        portfolioDesign.image = image;
        portfolioDesign.explain = explain;
        return portfolioDesign;
    }

    public void update(Image image, String explain) {
        this.image = image;
        this.explain = explain;
    }

    public void remove() {
        image.remove();
    }
}
