package start.capstone2.domain.techstack;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import start.capstone2.domain.Image.Image;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TechStack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "techstack_id")
    private Long id;

    private String name;
    
    @Setter
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id")
    private Image image;


    public static TechStack createTechStack(String name, Image image) {
        TechStack techStack = new TechStack();
        techStack.name = name;
        techStack.image = image;

        return techStack;
    }

}
