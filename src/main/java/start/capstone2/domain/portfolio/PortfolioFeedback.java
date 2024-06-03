package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import start.capstone2.domain.BaseEntity;
import start.capstone2.domain.user.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PortfolioFeedback extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_feedback_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    private String content;

    // TODO
    private String page;
    private Integer location;

    @Builder
    private PortfolioFeedback(User user, Portfolio portfolio, String content, String page, Integer location) {
        this.user = user;
        this.portfolio = portfolio;
        this.content = content;
        this.page = page;
        this.location = location;
    }

    public void updateFeedback(String content, Integer location) {
        this.content = content;
        this.location = location;
    }

}
