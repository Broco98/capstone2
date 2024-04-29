package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import start.capstone2.domain.Image.Image;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PortfolioImage {

    @Id
    @GeneratedValue
    @Column(name = "portfolio_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    private Portfolio portfolio;

    @ManyToOne
    private Image image;

    public static PortfolioImage createPortfolioImage(Image image) {
        PortfolioImage portfolioImage = new PortfolioImage();
        portfolioImage.image = image;

        return portfolioImage;
    }

    public void remove() {
        image.remove();
    }
}
