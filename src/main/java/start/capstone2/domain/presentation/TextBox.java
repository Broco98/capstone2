package start.capstone2.domain.presentation;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="text_box")
public class TextBox {

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

    @Column(nullable = true, length = 1024)
    private String text;

    @Column(nullable = true)
    private String font_family;  // 폰트 패밀리

    @Column(nullable = true)
    private Double font_size;    // 글자 크기

    @Column(nullable = true)
    private Boolean is_bold;     // 글자 진하게

    @Column(nullable = true)
    private Boolean is_italic;   // 글자 기울이기

    @Column(nullable = true)
    private String text_color;   // 글자 기울이기

    @Column(nullable = true)
    private String text_align;   // 글자 기울이기

    public TextBox cloneTextBox() {
        TextBox clone = new TextBox();
        clone.setPosition_x(this.position_x);
        clone.setPosition_y(this.position_y);
        clone.setWidth(this.width);
        clone.setHeight(this.height);
        clone.setFont_family(this.font_family);
        clone.setFont_size(this.font_size);
        clone.setIs_bold(this.is_bold);
        clone.setIs_italic(this.is_italic);
        clone.setText_align(this.text_align);
        clone.setBox_type(this.box_type);

        if ("constant".equals(this.box_type)) {
            clone.setText(this.text);
        }

        return clone;
    }
}
