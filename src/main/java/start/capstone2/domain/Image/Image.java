package start.capstone2.domain.Image;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String originName;
    private String savedName;

    @Builder
    private Image(String originName, String savedName) {
        this.originName = originName;
        this.savedName = savedName;
    }
}
