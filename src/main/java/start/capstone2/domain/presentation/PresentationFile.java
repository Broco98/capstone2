package start.capstone2.domain.presentation;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class PresentationFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long portfolioId;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] fileData;
}
