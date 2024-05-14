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

    private String originalName;
    private String savedName;

    public static Image createEmptyImage() {
        return new Image();
    }

    public static Image createImage(String originalName, String savedName) {
        Image image = new Image();
        image.originalName = originalName;
        image.savedName = savedName;

        return image;
    }

    public void remove() {
        ImageStore.removeImage(this);
    }
}
