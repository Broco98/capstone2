package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import start.capstone2.domain.user.User;

@Entity
public class PortfolioUrl {

    @Id
    @GeneratedValue
    @Column(name = "portfolio_url")
    private Long id;

    // TODO: 보류, url을 어떻게 해야할지 햇갈림



}
