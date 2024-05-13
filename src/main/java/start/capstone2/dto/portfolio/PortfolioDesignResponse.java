package start.capstone2.dto.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class PortfolioDesignResponse {
    private Long id;
    private String imageUrl;
    private String explain;
}
