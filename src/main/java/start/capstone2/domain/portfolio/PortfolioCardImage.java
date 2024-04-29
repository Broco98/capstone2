package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import start.capstone2.domain.Image.Image;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PortfolioCardImage {

    @Id
    @GeneratedValue
    @Column(name = "portfolio_card_image_id")
    private Long id;

    @ManyToOne
    private Portfolio portfolio;

    @ManyToOne
    private Image image;

    public static PortfolioCardImage createPortfolioCardImage(Portfolio portfolio, Image image) {
        PortfolioCardImage portfolioCardImage = new PortfolioCardImage();
        portfolioCardImage.portfolio = portfolio;
        portfolioCardImage.image = image;

        return portfolioCardImage;
    }

    public void remove() {
        image.remove();
    }
}
