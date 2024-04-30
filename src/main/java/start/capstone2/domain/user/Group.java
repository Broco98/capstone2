package start.capstone2.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue
    @Column(name="group_id")
    private Long id;
    private String name;

    public static Group createGroup(String name) {
        Group group = new Group();
        group.name = name;

        return group;
    }
}
