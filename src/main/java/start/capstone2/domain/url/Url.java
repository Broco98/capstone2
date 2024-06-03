package start.capstone2.domain.url;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import start.capstone2.domain.portfolio.PortfolioUrl;
import start.capstone2.domain.user.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "url_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String url;

    @OneToMany(mappedBy = "url", cascade = CascadeType.ALL, orphanRemoval = true)
    List<PortfolioUrl> portfolioUrls = new ArrayList<>();


    @Builder
    private Url(User user, String url, List<PortfolioUrl> portfolioUrls) {
        this.user = user;
        this.url = url;
        this.portfolioUrls = portfolioUrls;
    }

    public void addPortfolioUrl(PortfolioUrl portfolioUrl) {
        portfolioUrls.add(portfolioUrl);
    }

    public void removePortfolioUrl(PortfolioUrl portfolioUrl){
        portfolioUrls.remove(portfolioUrl);
    }

}
