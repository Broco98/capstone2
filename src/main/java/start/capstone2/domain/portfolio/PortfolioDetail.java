package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import start.capstone2.domain.BaseEntity;
import start.capstone2.domain.user.User;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PortfolioDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_detail")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate startDate;
    private LocalDate endDate;
    private Integer teamNum;
    private String title;
    private String purpose;
    private Integer contribution;

    public static PortfolioDetail createPortfolioDetail(User user, LocalDate startDate, LocalDate endDate, Integer teamNum, String title, String purpose, Integer contribution) {
        PortfolioDetail detail = new PortfolioDetail();
        detail.user = user;
        detail.startDate = startDate;
        detail.endDate = endDate;
        detail.teamNum = teamNum;
        detail.title = title;
        detail.purpose = purpose;
        detail.contribution = contribution;
        return detail;
    }

    public void updatePortfolioDetail(LocalDate startDate, LocalDate endDate, Integer teamNum, String title, String purpose, Integer contribution) {
        this.startDate = startDate;
        this.purpose = purpose;
        this.endDate = endDate;
        this.teamNum = teamNum;
        this.title = title;
        this.contribution = contribution;
    }
}
