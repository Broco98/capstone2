package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import start.capstone2.domain.BaseEntity;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PortfolioDatabase extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_datebase_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @Lob
    private String schema;

    @Lob
    private String description;


    public void updateDatabase(String schema, String description) {
        this.schema = schema;
        this.description = description;
    }

}
