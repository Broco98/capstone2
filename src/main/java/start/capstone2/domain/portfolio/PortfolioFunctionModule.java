package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PortfolioFunctionModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_function_module_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_function_id")
    private PortfolioFunction function;

    private String name;

    @Lob
    private String description;

    @Builder
    private PortfolioFunctionModule(PortfolioFunction function, String name, String description) {
        this.function = function;
        this.name = name;
        this.description = description;
    }

    public void updateFunctionModule(String name, String description) {
        this.name = name;
        this.description = description;
    }


}
