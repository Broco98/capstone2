package start.capstone2.dto.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PortfolioTechStackResponse {
    private Long id;
    private String name;
    private String imageUrl;
}
