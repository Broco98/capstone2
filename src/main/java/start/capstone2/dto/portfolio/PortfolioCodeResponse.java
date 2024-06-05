package start.capstone2.dto.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PortfolioCodeResponse {
    private Long id;
    private String name;
    private String code;
    private String description;
}
