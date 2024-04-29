package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import start.capstone2.domain.user.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PortfolioComment {

    @Id
    @GeneratedValue
    @Column(name = "portfolio_comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Portfolio portfolio;

    private String content;


    public static PortfolioComment createPortfolioComment(User user, Portfolio portfolio, String content) {
        PortfolioComment comment = new PortfolioComment();
        comment.user = user;
        comment.portfolio = portfolio;
        comment.content = content;

        return comment;
    }

    public void updateContent(String content) {
        this.content = content;
    }

}
