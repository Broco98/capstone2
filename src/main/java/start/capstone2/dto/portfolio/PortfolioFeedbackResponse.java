package start.capstone2.dto.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PortfolioFeedbackResponse {
    private Long id;
    private String content;
    private String page;
    private Integer location;
}
