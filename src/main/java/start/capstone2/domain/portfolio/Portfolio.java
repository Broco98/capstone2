package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import start.capstone2.domain.user.Group;
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
    @JoinColumn(name = "user_id")
    private User user;
    
    // 나를 제외한 멤버
//    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
//    private List<PortfolioMember> members = new ArrayList<>();
    private Integer memberNum;

    @Column(nullable = false)
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer contribution;
    private String purpose;

    @Lob
    private String content;
    
    // 공개 그룹들
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<PortfolioGroup> sharedGroups = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<PortfolioPost> posts = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio")
    private List<PortfolioComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio")
    private List<PortfolioFeedback> feedbacks = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "portfolio_card_image_id")
    private PortfolioCardImage cardImage;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<PortfolioImage> images = new ArrayList<>();

    public static Portfolio createPortfolio(
            User user,
            String title,
            LocalDate startDate,
            LocalDate endDate,
            Integer contribution,
            String purpose, String content,
            PortfolioCardImage cardImage,
            List<PortfolioImage> images,
            List<PortfolioGroup> shardGroups,
            Integer memberNum) {

        Portfolio portfolio = new Portfolio();
        portfolio.user = user;
        portfolio.title = title;
        portfolio.startDate = startDate;
        portfolio.endDate = endDate;
        portfolio.contribution = contribution;
        portfolio.purpose = purpose;
        portfolio.content = content;
        portfolio.cardImage = cardImage;
        portfolio.memberNum = memberNum;

        if (images != null && !images.isEmpty()) {
            for (PortfolioImage image : images) {
                portfolio.addImage(image);
            }
        }

        if (shardGroups != null && !shardGroups.isEmpty()) {
            for (PortfolioGroup group : shardGroups) {
                portfolio.addGroup(group);
            }
        }

        return portfolio;
    }

    public void updatePortfolio(
            String title,
            LocalDate startDate,
            LocalDate endDate,
            Integer contribution,
            String purpose,
            String content,
            PortfolioCardImage cardImage,
            List<PortfolioImage> images,
            List<PortfolioGroup> sharedGroups,
            Integer memberNum) {

        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contribution = contribution;
        this.purpose = purpose;
        this.content = content;

        if (cardImage != null) {
           changeCardImage(cardImage);
        }

        this.memberNum = memberNum;

        if (images != null && !images.isEmpty()) {
            List<PortfolioImage> remove = new ArrayList<>();

            for (PortfolioImage image : this.images) {
                if (!images.contains(image))
                    remove.add(image);
            }

            for (PortfolioImage image : images) {
                if (!this.images.contains(image)) {
                    this.addImage(image);
                }
            }

            this.images.removeAll(remove);
        }

        if (sharedGroups != null && !sharedGroups.isEmpty()) {
            List<PortfolioGroup> remove = new ArrayList<>();

            for (PortfolioGroup group : this.sharedGroups) {
                if (!sharedGroups.contains(group))
                    remove.add(group);
            }

            for (PortfolioGroup group : sharedGroups) {
                if (!this.sharedGroups.contains(group)) {
                    this.addGroup(group);
                }
            }

            this.sharedGroups.removeAll(remove);
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

    public void addGroup(PortfolioGroup group) {
        sharedGroups.add(group);
        group.setPortfolio(this);
    }

    public void changeCardImage(PortfolioCardImage cardImage) {
        this.cardImage = cardImage;
        cardImage.setPortfolio(this);
    }
}
