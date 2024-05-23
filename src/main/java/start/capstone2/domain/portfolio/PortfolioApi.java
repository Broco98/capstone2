package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import start.capstone2.domain.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PortfolioApi extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_api_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @Enumerated(EnumType.STRING)
    private Method method;
    private String url;
    private String description;
    private String response;

    public static PortfolioApi createPortfolioApi(Portfolio portfolio, Method method, String url, String description, String response) {
        PortfolioApi portfolioApi = new PortfolioApi();
        portfolioApi.portfolio = portfolio;
        portfolioApi.method = method;
        portfolioApi.url = url;
        portfolioApi.description = description;
        portfolioApi.response = response;

        return portfolioApi;
    }

    public void updatePortfolioApi(Method method, String url, String description, String response) {
        this.method = method;
        this.url = url;
        this.description = description;
        this.response = response;
    }
}
