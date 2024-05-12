package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import start.capstone2.domain.url.Url;
import start.capstone2.domain.user.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PortfolioUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_url_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @ManyToOne
    @JoinColumn(name = "url_id")
    private Url url;


    public static PortfolioUrl createPortfolioUrl(Portfolio portfolio, Url url) {
        PortfolioUrl portfolioUrl = new PortfolioUrl();
        portfolioUrl.portfolio = portfolio;
        portfolioUrl.url = url;
        return portfolioUrl;
    }

    // TODO
    public void remove() {

    }
}
