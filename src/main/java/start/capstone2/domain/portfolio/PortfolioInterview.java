package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import start.capstone2.domain.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PortfolioInterview extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "portfolio_post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @Lob
    private String question;

    @Lob
    private String answer;

    public static PortfolioInterview createPortfolioInterview(Portfolio portfolio, String question, String answer) {
        PortfolioInterview post = new PortfolioInterview();
        post.portfolio = portfolio;
        post.question = question;
        post.answer = answer;

        return post;
    }

    public void updatePortfolioInterview(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

}
