package start.capstone2.dto.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import start.capstone2.domain.portfolio.ShareStatus;

@Data
@AllArgsConstructor
public class PortfolioRequest {
    private Long id;
    private MultipartFile image;
    private ShareStatus status;
}
