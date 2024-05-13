package start.capstone2.dto.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PortfolioFeedbackRequest {
    private String content;
    private Integer page;
    private Integer location;
}
