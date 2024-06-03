package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import start.capstone2.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PortfolioDesign extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_design_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    private String name;

    @OneToMany(mappedBy = "design", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PortfolioDesignDiagram> diagrams = new ArrayList<>();

    @Builder
    private PortfolioDesign(Portfolio portfolio, String name) {
        this.portfolio = portfolio;
        this.name = name;
    }

    public void updatePortfolioDesign(String name) {
        this.name = name;
    }

    public void addDiagram(PortfolioDesignDiagram diagram) {
        diagrams.add(diagram);
    }

    public void removeDiagram(PortfolioDesignDiagram diagram) {
        diagrams.remove(diagram);
    }
}
