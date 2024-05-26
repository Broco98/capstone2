package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import start.capstone2.domain.BaseEntity;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PortfolioInterview extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "portfolio_post_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @Lob
    private String question;

    @Lob
    private String answer;


    public void updatePortfolioInterview(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

}
