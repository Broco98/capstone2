package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import start.capstone2.domain.BaseEntity;
import start.capstone2.domain.user.User;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PortfolioDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_detail_id")
    private Long id;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    private LocalDate startDate;
    private LocalDate endDate;
    private Integer teamNum;
    private String title;
    private String description;
    private Integer contribution;


    public static PortfolioDetail createEmptyDetail() {
        PortfolioDetail detail = new PortfolioDetail();
        return detail;
    }

    public static PortfolioDetail createPortfolioDetail(LocalDate startDate, LocalDate endDate, Integer teamNum, String title, String description, Integer contribution) {
        PortfolioDetail detail = new PortfolioDetail();
        detail.startDate = startDate;
        detail.endDate = endDate;
        detail.teamNum = teamNum;
        detail.title = title;
        detail.description = description;
        detail.contribution = contribution;
        return detail;
    }

    public void updatePortfolioDetail(LocalDate startDate, LocalDate endDate, Integer teamNum, String title, String description, Integer contribution) {
        this.startDate = startDate;
        this.description = description;
        this.endDate = endDate;
        this.teamNum = teamNum;
        this.title = title;
        this.contribution = contribution;
    }
}
