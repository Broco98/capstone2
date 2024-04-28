package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import start.capstone2.domain.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
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
    private LocalDateTime projectDate;
    private Integer contribution;
    private String purpose;

    @Lob
    private String content;

    @OneToMany(mappedBy = "portfolio")
    private List<PortfolioPost> posts = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio")
    private List<PortfolioComment> comments = new ArrayList<>();

}
