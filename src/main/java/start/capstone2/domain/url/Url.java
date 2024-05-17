package start.capstone2.domain.url;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import start.capstone2.domain.portfolio.PortfolioUrl;

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

    @OneToMany(mappedBy = "url", cascade = CascadeType.ALL, orphanRemoval = true)
    List<PortfolioUrl> portfolioUrls = new ArrayList<>();

    private String url;

    public static Url createUrl(String url) {
        Url myUrl = new Url();
        myUrl.url = url;
        return myUrl;
    }

    public void addPortfolioUrl(PortfolioUrl portfolioUrl) {
        portfolioUrls.add(portfolioUrl);
    }

    public void removePortfolioUrl(PortfolioUrl portfolioUrl){
        portfolioUrls.remove(portfolioUrl);
    }

}
