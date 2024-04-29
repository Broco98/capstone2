package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import lombok.*;
import start.capstone2.domain.Image.Image;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PortfolioPostImage {

    @Id
    @GeneratedValue
    @Column(name = "portfolio_post_image")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @Setter
    private PortfolioPost post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    public static PortfolioPostImage createPortfolioPostImage(Image image) {
        PortfolioPostImage portfolioPostImage = new PortfolioPostImage();
        portfolioPostImage.image = image;

        return portfolioPostImage;
    }

    public void remove() {
        image.remove();
    }
}
