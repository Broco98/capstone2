package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import start.capstone2.domain.user.User;

@Entity
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
}
