package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    @JoinColumn(name = "users_id")
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

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<PortfolioApi> apis = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<PortfolioCode> codes = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<PortfolioSchedule> schedules = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<PortfolioDesign> designs = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<PortfolioFunction> functions = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<PortfolioTechStack> techStacks = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<PortfolioUrl> urls = new ArrayList<>();

    @Setter
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image cardImage;

    private ShareStatus status;

    @Embedded
    private BaseEntity baseEntity;


    public static Portfolio createEmptyPortfolio(User user) {
        Portfolio portfolio = new Portfolio();
        portfolio.user = user;
        portfolio.status = ShareStatus.NONE; // 기본은 공유 X
        return portfolio;
    }

    public static Portfolio createPortfolio(
            User user,
            String title,
            LocalDate startDate,
            LocalDate endDate,
            Integer contribution,
            String purpose,
            Integer memberNum) {

        Portfolio portfolio = new Portfolio();
        portfolio.user = user;
        portfolio.title = title;
        portfolio.startDate = startDate;
        portfolio.endDate = endDate;
        portfolio.contribution = contribution;
        portfolio.purpose = purpose;
        portfolio.memberNum = memberNum;
        portfolio.status = ShareStatus.NONE; // 기본은 공유 X
        return portfolio;
    }

    public void updatePortfolio(
            String title,
            LocalDate startDate,
            LocalDate endDate,
            Integer contribution,
            String purpose,
            Integer memberNum,
            Image cardImage,
            ShareStatus status) {

        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contribution = contribution;
        this.purpose = purpose;
        this.memberNum = memberNum;
        this.cardImage = cardImage;
        this.status = status;
    }

    // TODO -> 이미지 등 삭제해야 하므로
    public void deletePortfolio() {
        for (PortfolioDesign design : designs) {
            design.remove();
        }

        for (PortfolioFunction function : functions) {
            function.remove();
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
}
