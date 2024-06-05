package start.capstone2.dto.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
public class PortfolioFunctionResponse {
    private Long id;
    private String name;
    private List<PortfolioFunctionModuleResponse> functions;
}
