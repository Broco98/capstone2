package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import start.capstone2.domain.BaseEntity;
import start.capstone2.domain.Image.Image;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PortfolioDesignDiagram extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_design_diagram_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_design_id")
    private PortfolioDesign design;

    @Lob
    private String diagram;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id")
    private Image image;

    @Lob
    private String description;

    @Builder
    private PortfolioDesignDiagram(PortfolioDesign design, Image image, String diagram, String description) {
        this.design = design;
        this.diagram = diagram;
        this.image = image;
        this.description = description;
    }

    public void updatePortfolioDesignDiagram(String diagram, Image image, String description) {
        this.diagram = diagram;
        this.image = image;
        this.description = description;
    }
}
