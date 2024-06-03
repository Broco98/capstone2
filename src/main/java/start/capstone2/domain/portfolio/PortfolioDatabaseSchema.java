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
public class PortfolioDatabaseSchema extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_datebase_schema_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_database_id")
    private PortfolioDatabase database;

    @Lob
    @Column(name = "db_schema")
    private String schema;

    @Lob
    private String description;

    @Builder
    private PortfolioDatabaseSchema(PortfolioDatabase database, String schema, String description) {
        this.database = database;
        this.schema = schema;
        this.description = description;
    }

    public void updateDatabaseSchema(String schema, String description) {
        this.schema = schema;
        this.description = description;
    }

}
