package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import start.capstone2.domain.user.User;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class PortfolioLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @CreatedDate
    private LocalDateTime createdDate;


    public PortfolioLike createPortfolioLike(User user, Portfolio portfolio) {
        PortfolioLike like = new PortfolioLike();
        like.user = user;
        like.portfolio = portfolio;
        return like;
    }

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();;
    }

}
