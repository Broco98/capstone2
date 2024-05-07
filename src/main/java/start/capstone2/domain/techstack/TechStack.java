package start.capstone2.domain.techstack;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import start.capstone2.domain.Image.Image;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TechStack {

    @Id
    @GeneratedValue
    @Column(name = "techstack_id")
    private Long id;

    private String name;

    @Embedded
    private Image image;


    public static TechStack createTechStack(String name, Image image) {
        TechStack techStack = new TechStack();
        techStack.name = name;
        techStack.image = image;

        return techStack;
    }
}
