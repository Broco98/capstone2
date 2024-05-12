package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public static PortfolioPost createPortfolioPost(String title, String content) {
        PortfolioPost post = new PortfolioPost();
        post.title = title;
        post.content = content;

        return post;
    }

    public void updatePortfolioPost(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
