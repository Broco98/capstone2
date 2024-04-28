package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import start.capstone2.domain.Image.Image;

@Entity
public class PortfolioCardImage {

    @Id
    @GeneratedValue
    @Column(name = "portfolio_card_image_id")
    private Long id;

    @ManyToOne
    private Portfolio portfolio;

    @ManyToOne
    private Image image;
}
