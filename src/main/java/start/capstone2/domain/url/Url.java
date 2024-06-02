package start.capstone2.domain.url;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import start.capstone2.domain.portfolio.PortfolioUrl;
import start.capstone2.domain.user.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "url_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private String url;

    @Builder.Default
    @OneToMany(mappedBy = "url", cascade = CascadeType.ALL, orphanRemoval = true)
    List<PortfolioUrl> portfolioUrls = new ArrayList<>();



    public void addPortfolioUrl(PortfolioUrl portfolioUrl) {
        portfolioUrls.add(portfolioUrl);
    }

    public void removePortfolioUrl(PortfolioUrl portfolioUrl){
        portfolioUrls.remove(portfolioUrl);
    }

}
