package start.capstone2.dto.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import start.capstone2.domain.portfolio.PortfolioDesignDiagram;

import java.util.List;

@Data
@AllArgsConstructor
public class PortfolioDesignResponse {
    private Long id;
    private String name;
    private List<PortfolioDesignDiagramResponse> diagrams;
}
