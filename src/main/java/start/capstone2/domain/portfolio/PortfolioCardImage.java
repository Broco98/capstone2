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
public class PortfolioCardImage{

    @Id
    @GeneratedValue
    @Column(name = "portfolio_card_image_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    @Setter
    private Portfolio portfolio;

//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "image_id")
//    private Image image;

    @Embedded
    private Image image;

    public static PortfolioCardImage createPortfolioCardImage(Image image) {
        PortfolioCardImage portfolioCardImage = new PortfolioCardImage();
        portfolioCardImage.image = image;

        return portfolioCardImage;
    }

    public void remove() {
        image.remove();
    }
}
