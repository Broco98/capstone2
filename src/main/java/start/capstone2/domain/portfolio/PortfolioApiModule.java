package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import start.capstone2.domain.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PortfolioApiModule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_api_module_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_api_id")
    private PortfolioApi api;

    @Enumerated(EnumType.STRING)
    private Method method;
    private String url;
    private String description;
    private String response;

    @Builder
    public PortfolioApiModule(PortfolioApi api, Method method, String url, String description, String response) {
        this.api = api;
        this.method = method;
        this.url = url;
        this.description = description;
        this.response = response;
    }

    public void updatePortfolioApiModule(Method method, String url, String description, String response) {
        this.method = method;
        this.url = url;
        this.description = description;
        this.response = response;
    }
}
