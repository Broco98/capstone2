package start.capstone2.domain.portfolio;

import jakarta.persistence.*;

@Entity
public class PortfolioPost {

    @Id
    @GeneratedValue
    @Column(name = "portfolio_post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Portfolio portfolio;

    private String title;

    @Lob
    private String content;
}
