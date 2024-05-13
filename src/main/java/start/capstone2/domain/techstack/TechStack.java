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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "techstack_id")
    private Long id;

    private String name;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;


    public static TechStack createTechStack(String name, Image image) {
        TechStack techStack = new TechStack();
        techStack.name = name;
        techStack.image = image;

        return techStack;
    }

    public void deleteTechStack() {
        image.remove();
    }
}
