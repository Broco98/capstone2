package start.capstone2.domain.user;

import jakarta.persistence.*;

@Entity
@Table(name = "user_group")
public class UserGroup {

    @Id
    @GeneratedValue
    @Column(name = "user_group_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;
}
