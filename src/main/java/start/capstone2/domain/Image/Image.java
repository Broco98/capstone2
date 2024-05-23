package start.capstone2.domain.Image;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.StringTokenizer;
import java.util.UUID;


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

    public static Image createEmptyImage() {
        return new Image();
    }

    public static Image createImage(String originName, String savedName) {
        Image image = new Image();
        image.originName = originName;
        image.savedName = savedName;

        return image;
    }
}
