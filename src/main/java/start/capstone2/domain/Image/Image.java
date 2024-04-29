package start.capstone2.domain.Image;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    private String originalName;
    private String savedName;

    public static Image createImage(String originalName) {
        Image image = new Image();
        image.originalName = originalName;

        // TODO: savedName (Path) 만들기

        return image;
    }

    // TODO: image 삭제 메서드 작성하기
    public void remove() {

    }
}
