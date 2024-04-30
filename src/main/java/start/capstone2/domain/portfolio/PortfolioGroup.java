package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import start.capstone2.domain.user.Group;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PortfolioGroup {

    @Id
    @GeneratedValue
    @Column(name = "portfolio_group_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    @Setter
    private Portfolio portfolio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;


    public static PortfolioGroup createPortfolioGroup(Group group) {
        PortfolioGroup portfolioGroup = new PortfolioGroup();
        portfolioGroup.group = group;

        return portfolioGroup;
    }
}
