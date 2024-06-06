package start.capstone2.domain.presentation;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="presentation")
public class Presentation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long presentation_id;

    @Column(name = "presentation_type", nullable = false)
    private String presentationType;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "presentation_id")
    @OrderBy("id ASC")
    private List<Slide> slides = new ArrayList<>();
}
