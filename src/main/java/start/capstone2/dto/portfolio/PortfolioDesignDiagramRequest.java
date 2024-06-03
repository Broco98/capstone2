package start.capstone2.dto.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class PortfolioDesignDiagramRequest {
    private MultipartFile image;
    private String diagram;
    private String description;
}
