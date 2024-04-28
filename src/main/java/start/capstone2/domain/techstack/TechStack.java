package start.capstone2.domain.techstack;

import jakarta.persistence.*;
import start.capstone2.domain.Image.Image;

@Entity
public class TechStack {

    @Id
    @GeneratedValue
    @Column(name = "techstack_id")
    private Long id;

    private String name;

    @ManyToOne
    private Image image;
}
