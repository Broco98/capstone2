package start.capstone2.dto.portfolio;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import start.capstone2.domain.Image.Image;

@Data
@AllArgsConstructor
public class PortfolioDesignRequest {

    private MultipartFile image;
    private String explain;

}
