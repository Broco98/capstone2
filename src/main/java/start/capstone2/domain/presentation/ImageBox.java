package start.capstone2.domain.presentation;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="image_box")
public class ImageBox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slide_id", nullable = false)
    private Slide slide;

    @Column(name = "box_type", nullable = false)
    private String box_type;

    @Column(name = "position_x", nullable = false)
    private double position_x;

    @Column(name = "position_y", nullable = false)
    private double position_y;

    @Column(name = "width", nullable = false)
    private double width;

    @Column(name = "height", nullable = false)
    private double height;

    @Column(nullable = true)
    private String image_url;

    public ImageBox cloneImageBox() {
        ImageBox clone = new ImageBox();
        clone.setPosition_x(this.position_x);
        clone.setPosition_y(this.position_y);
        clone.setWidth(this.width);
        clone.setHeight(this.height);
        return clone;
    }
}