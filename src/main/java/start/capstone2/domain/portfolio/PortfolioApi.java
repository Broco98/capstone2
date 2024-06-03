package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import start.capstone2.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;

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

    private String name;

    @OneToMany(mappedBy = "api", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PortfolioApiModule> modules = new ArrayList<>();

    @Builder
    public PortfolioApi(Portfolio portfolio, String name) {
        this.portfolio = portfolio;
        this.name = name;
    }

    public void updatePortfolioApi(String name) {
        this.name = name;
    }

    public void addModule(PortfolioApiModule module) {
        modules.add(module);
    }

    public void removeModule(PortfolioApiModule module) {
        modules.remove(module);
    }
}
