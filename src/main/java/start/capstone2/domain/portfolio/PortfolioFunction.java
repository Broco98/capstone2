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
public class PortfolioFunction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_function_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    private String name;

    @OneToMany(mappedBy = "function", cascade = CascadeType.ALL, orphanRemoval = true)
    List<PortfolioFunctionModule> modules = new ArrayList<>();

    @Builder
    private PortfolioFunction(Portfolio portfolio, String name) {
        this.portfolio = portfolio;
        this.name = name;
    }

    public void updatePortfolioFunction(String name) {
        this.name = name;
    }

    public void addModule(PortfolioFunctionModule module) {
        modules.add(module);
    }

    public void removeModule(PortfolioFunctionModule module) {
        modules.remove(module);
    }

}
