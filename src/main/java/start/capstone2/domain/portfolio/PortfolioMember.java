package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import start.capstone2.domain.user.User;

import java.util.ArrayList;
import java.util.List;

@Entity
public class PortfolioMember {

    @Id
    @GeneratedValue
    @Column(name = "portfolio_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Portfolio portfolio;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
