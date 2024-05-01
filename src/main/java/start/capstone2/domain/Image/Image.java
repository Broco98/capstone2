package start.capstone2.domain.Image;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.StringTokenizer;
import java.util.UUID;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "dtype")
public class Image {

    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    private String uploadName;
    private String savedName;

    public static Image createImage(String uploadName, String savedName) {
        Image image = new Image();
        image.uploadName = uploadName;
        image.savedName = savedName;

        return image;
    }

    public void remove() {

    }
}
