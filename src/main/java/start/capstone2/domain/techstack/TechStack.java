package start.capstone2.domain.techstack;

import jakarta.persistence.*;
import lombok.*;
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


    @Builder
    private TechStack(String name, Image image) {
        this.name = name;
        this.image = image;
    }
}
