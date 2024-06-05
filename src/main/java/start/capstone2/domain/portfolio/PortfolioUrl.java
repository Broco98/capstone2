//package start.capstone2.domain.portfolio;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;
//import lombok.*;
//import start.capstone2.domain.BaseEntity;
//import start.capstone2.domain.url.Url;
//
//@Entity
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class PortfolioUrl extends BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "portfolio_url_id")
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "portfolio_id")
//    private Portfolio portfolio;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "url_id")
//    private Url url;
//
//    @Builder
//    private PortfolioUrl(Portfolio portfolio, Url url) {
//        this.portfolio = portfolio;
//        this.url = url;
//    }
//}
