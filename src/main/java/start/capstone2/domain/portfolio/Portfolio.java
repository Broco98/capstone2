package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import start.capstone2.domain.BaseEntity;
import start.capstone2.domain.Image.Image;
import start.capstone2.domain.user.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer contribution;
    private String purpose;
    private Integer memberNum;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<PortfolioPost> posts = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio")
    private List<PortfolioComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio")
    private List<PortfolioFeedback> feedbacks = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio")
    private List<PortfolioApi> apis = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio")
    private List<PortfolioCode> codes = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio")
    private List<PortfolioSchedule> schedules = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio")
    private List<PortfolioDesign> designs = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio")
    private List<PortfolioFunction> functions = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio")
    private List<PortfolioTechStack> techStacks = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio")
    private List<PortfolioUrl> urls = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image cardImage;

    private ShareStatus status;

    @Embedded
    private BaseEntity baseEntity;

    // TODO
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

    public void addDesign(PortfolioDesign design) {
        designs.add(design);
    }

    public void removeDesign(PortfolioDesign design) {
        designs.remove(design);
    }

    public void addApi(PortfolioApi api) {
        apis.add(api);
    }

    public void removeApi(PortfolioApi api) {
        apis.remove(api);
    }

    public void addFunction(PortfolioFunction function) {
        functions.add(function);
    }

    public void removeFunction(PortfolioFunction function) {
        functions.remove(function);
    }

    public void addSchedule(PortfolioSchedule schedule) {
        schedules.add(schedule);
    }

    public void removeSchedule(PortfolioSchedule schedule) {
        schedules.remove(schedule);
    }

    public void addCode(PortfolioCode code) {
        codes.add(code);
    }

    public void removeCode(PortfolioCode code) {
        codes.remove(code);
    }

    public void updateCardImage(Image image) {
        cardImage.remove();
        cardImage = image;
    }
}
