package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import start.capstone2.domain.Image.Image;
import start.capstone2.domain.user.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image cardImage;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "portfolio_detail_id")
    private PortfolioDetail detail;


    public static Portfolio createPortfolio(User user, Image cardImage, PortfolioDetail detail) {
        Portfolio portfolio = new Portfolio();
        portfolio.user = user;
        portfolio.cardImage = cardImage;
        portfolio.detail = detail;
        return portfolio;
    }

    public void updateCardImage(Image cardImage) {
        this.cardImage.remove();
        this.cardImage = cardImage;
    }

    public void remove() {
        cardImage.remove();
        detail.remove();
    }
}
