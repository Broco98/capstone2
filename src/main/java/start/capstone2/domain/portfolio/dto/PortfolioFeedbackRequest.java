package start.capstone2.domain.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PortfolioFeedbackRequest {

    private String content;
    private Integer location;

}
