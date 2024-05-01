package start.capstone2.domain.portfolio;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import start.capstone2.domain.user.User;

import java.util.ArrayList;
import java.util.List;

// 필요 없을 듯
//@Entity
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PortfolioMember {

//    @Id
//    @GeneratedValue
//    @Column(name = "portfolio_member_id")
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "portfolio_id")
//    @Setter // portfolio 생성 전에 등록될 수 있음 -> 나중에 setter를 사용해서 등록
//    private Portfolio portfolio;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @Column(nullable = false)
//    private String name;
//
//
//    public PortfolioMember createPortfolioMember(User user, String name) {
//        PortfolioMember member = new PortfolioMember();
//        member.user = user;
//        member.name = name;
//
//        return member;
//    }
}
