package start.capstone2.domain.presentation;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "slide")
public class Slide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String slide_type;

    @Column(nullable = true)
    private String theme_color;

    @Column(name = "text_count", nullable = false)
    private int text_count;  // TextBox 컴포넌트의 수

    @Column(name = "image_count", nullable = false)
    private int image_count; // ImageBox 컴포넌트의 수

    @OneToMany(mappedBy = "slide", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TextBox> textBoxes = new ArrayList<>();

    @OneToMany(mappedBy = "slide", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ImageBox> imageBoxes = new ArrayList<>();

    @OneToMany(mappedBy = "slide", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Shape> shapes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "presentation_id", nullable = true)
    private Presentation presentation;

}