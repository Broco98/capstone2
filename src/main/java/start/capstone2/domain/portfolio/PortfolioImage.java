package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import start.capstone2.domain.Image.Image;

@Entity
public class PortfolioImage {

    @Id
    @GeneratedValue
    @Column(name = "portfolio_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Portfolio portfolio;

    @ManyToOne
    private Image image;

}
