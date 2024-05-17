package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import lombok.*;
import start.capstone2.domain.BaseEntity;
import start.capstone2.domain.Image.Image;
import start.capstone2.domain.user.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Portfolio extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image cardImage;

    @Setter
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "portfolio_detail_id")
    private PortfolioDetail detail;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PortfolioDesign> designs = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PortfolioApi> apis = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PortfolioCode> codes = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PortfolioFeedback> feedbacks = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PortfolioComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PortfolioFunction> functions = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PortfolioSchedule> schedules = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PortfolioPpt> ppts = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PortfolioUrl> urls = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PortfolioPost> posts = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PortfolioTechStack> techStacks = new ArrayList<>();

    private ShareStatus status;

    public static Portfolio createPortfolio(User user, Image cardImage, PortfolioDetail detail) {
        Portfolio portfolio = new Portfolio();
        portfolio.user = user;
        portfolio.cardImage = cardImage;
        portfolio.detail = detail;
        portfolio.status = ShareStatus.NONE;
        return portfolio;
    }

    public void updatePortfolio(Image cardImage, ShareStatus status) {
        this.cardImage.remove();
        this.cardImage = cardImage;
        this.status = status;
    }


    public void deletePortfolio() {
        // TODO: 이미지 삭제 필요함
        cardImage.remove();
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

    public void addTechStack(PortfolioTechStack stack) {
        techStacks.add(stack);
    }

    public void removeTechStack(PortfolioTechStack stack) {
        techStacks.remove(stack);
    }

    public void addUrl(PortfolioUrl url) {
        urls.add(url);
    }

    public void removeUrl(PortfolioUrl url) {
        urls.remove(url);
    }
}
