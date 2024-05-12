package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import start.capstone2.domain.BaseEntity;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PortfolioSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_schedule_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    @Setter
    private Portfolio portfolio;

    private LocalDate startDate;
    private LocalDate endDate;

    @Lob
    private String explain;

    @Embedded
    private BaseEntity baseEntity;


    public static PortfolioSchedule createPortfolioSchedule(LocalDate startDate, LocalDate endDate, String explain) {
        PortfolioSchedule schedule = new PortfolioSchedule();
        schedule.startDate = startDate;
        schedule.endDate = endDate;
        schedule.explain = explain;
        return schedule;
    }
}
