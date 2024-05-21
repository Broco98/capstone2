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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;

    @Lob
    private String description;


    public static PortfolioDesign createPortfolioDesign(Portfolio portfolio, Image image, String description) {
        PortfolioDesign design = new PortfolioDesign();
        design.portfolio = portfolio;
        design.image = image;
        design.description = description;
        return design;
    }

    public void updatePortfolioDesign(Image image, String description) {
        this.image = image;
        this.description = description;
    }
}
