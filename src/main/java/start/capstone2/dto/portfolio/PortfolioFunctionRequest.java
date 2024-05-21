package start.capstone2.dto.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class PortfolioFunctionRequest {
    private String description;
}
