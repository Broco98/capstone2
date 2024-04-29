package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PortfolioPostImage> images = new ArrayList<>();

    public static PortfolioPost createPortfolioPost(String title, String content) {
        PortfolioPost post = new PortfolioPost();
        post.title = title;
        post.content = content;

        return post;
    }

    public void addImage(PortfolioPostImage image) {
        images.add(image);
        image.setPost(this);
    }

    public void removeImage(PortfolioPostImage image) {
        images.remove(image);
        image.remove();
    }

    public void updateDefaultInfo(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void remove() {
        for (PortfolioPostImage image : images) {
            image.remove();
        }
        images.clear();
    }

}
