package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import start.capstone2.domain.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Portfolio {

    @Id
    @GeneratedValue
    @Column(name = "portfolio_id")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    
    @OneToMany(mappedBy = "user")
    private List<PortfolioMember> members = new ArrayList<>();
    
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer contribution;
    private String purpose;

    @Lob
    private String content;

    @OneToMany(mappedBy = "portfolio")
    private List<PortfolioPost> posts = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio")
    private List<PortfolioComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio")
    private List<PortfolioFeedback> feedbacks = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_card_image_id")
    private PortfolioCardImage cardImage;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<PortfolioImage> images = new ArrayList<>();

    public static Portfolio createPortfolio(User user, String title, LocalDateTime startDate, LocalDateTime endDate, Integer contribution, String purpose, String content, PortfolioCardImage cardImage, List<PortfolioImage> images) {
        Portfolio portfolio = new Portfolio();
        portfolio.user = user;
        portfolio.title = title;
        portfolio.startDate = startDate;
        portfolio.endDate = endDate;
        portfolio.contribution = contribution;
        portfolio.purpose = purpose;
        portfolio.content = content;
        portfolio.cardImage = cardImage;

        if (images != null && !images.isEmpty()) {
            for (PortfolioImage image : images) {
                portfolio.addImage(image);
            }
        }

        return portfolio;
    }

    public void update(String title, LocalDateTime startDate, LocalDateTime endDate, Integer contribution, String purpose, String content, PortfolioCardImage cardImage, List<PortfolioImage> images) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contribution = contribution;
        this.purpose = purpose;
        this.content = content;
        this.cardImage = cardImage;

        if (images != null && !images.isEmpty()) {
            this.images.clear();
            this.images.addAll(images);
        }
    }

    public void addPost(PortfolioPost post) {
        posts.add(post);
    }

    public void removePost(PortfolioPost post) {
        posts.remove(post);
        post.remove();
    }

    public void addComment(PortfolioComment comment) {
        comments.add(comment);
    }

    public void removeComment(PortfolioComment comment) {
        comments.remove(comment);
    }

    public void addFeedback(PortfolioFeedback feedback) {
        feedbacks.add(feedback);
    }

    public void removeFeedback(PortfolioFeedback feedback) {
        feedbacks.remove(feedback);
    }

    public void updateCardImage(PortfolioCardImage image) {
        cardImage.remove();
        cardImage = image;
    }

    public void addImage(PortfolioImage image) {
        images.add(image);
        image.setPortfolio(this);
    }

}
