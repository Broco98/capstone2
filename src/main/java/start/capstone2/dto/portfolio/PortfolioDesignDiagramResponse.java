package start.capstone2.dto.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PortfolioDesignDiagramResponse {
    private Long id;
    private String diagram;
    private String description;
    private String imageName;
}
