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
public class PortfolioDatabase extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_datebase_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    private String name;

    @OneToMany(mappedBy = "database", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PortfolioDatabaseSchema> schemas = new ArrayList<>();

    @Builder
    private PortfolioDatabase(Portfolio portfolio, String name) {
        this.portfolio = portfolio;
        this.name = name;
    }

    public void updateDatabase(String name) {
        this.name = name;
    }

    public void addSchema(PortfolioDatabaseSchema schema) {
        schemas.add(schema);
    }

    public void removeSchema(PortfolioDatabaseSchema schema) {
        schemas.remove(schema);
    }

}
