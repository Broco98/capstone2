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
public class PortfolioApi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_api_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    private Method method;
    private String url;
    private String explain;
    private String response;

    @Embedded
    private BaseEntity baseEntity;


    public static PortfolioApi createPortfolioApi(Portfolio portfolio, Method method, String url, String explain, String response) {
        PortfolioApi portfolioApi = new PortfolioApi();
        portfolioApi.portfolio = portfolio;
        portfolioApi.method = method;
        portfolioApi.url = url;
        portfolioApi.explain = explain;
        portfolioApi.response = response;

        return portfolioApi;
    }

    public void updatePortfolioApi(Method method, String url, String explain, String response) {
        this.method = method;
        this.url = url;
        this.explain = explain;
        this.response = response;
    }
}
