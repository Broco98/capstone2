package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import start.capstone2.domain.user.User;

@Entity
public class PortfolioUrl {

    @Id
    @GeneratedValue
    @Column(name = "portfolio_url")
    private Long id;

    // TODO: 보류, 작성 예정
    
}
