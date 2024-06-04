package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import lombok.*;
import start.capstone2.domain.BaseEntity;
import start.capstone2.domain.Image.Image;
import start.capstone2.domain.user.User;

import java.time.LocalDate;
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

    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer teamNum;

    @Lob
    private String description;

    @Lob
    private String contribution;

    @Setter
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id")
    private Image cardImage;

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
    private List<PortfolioUrl> urls = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PortfolioTechStack> techStacks = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PortfolioDatabase> databases = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ShareStatus status = ShareStatus.NOT_SHARED;

    @Builder
    private Portfolio(User user, String title, LocalDate startDate, LocalDate endDate, Integer teamNum, String description, String contribution, Image cardImage) {
        this.user = user;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teamNum = teamNum;
        this.description = description;
        this.contribution = contribution;
        this.cardImage = cardImage;
    }

    public void updatePortfolio(String title, LocalDate startDate, LocalDate endDate, Integer teamNum, String description, String contribution, Image cardImage, ShareStatus status) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teamNum = teamNum;
        this.description = description;
        this.contribution = contribution;
        this.cardImage = cardImage;
        this.status = status;
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

    public void addDatabase(PortfolioDatabase database) {
        databases.add(database);
    }

    public void removeDatabase(PortfolioDatabase database) {
        databases.remove(database);
    }

}
