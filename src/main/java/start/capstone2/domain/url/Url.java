package start.capstone2.domain.url;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "url_id")
    private Long id;

    private String url;


    public static Url createUrl(String url) {
        Url myUrl = new Url();
        myUrl.url = url;
        return myUrl;
    }
}
