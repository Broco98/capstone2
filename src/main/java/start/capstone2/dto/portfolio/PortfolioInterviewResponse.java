package start.capstone2.dto.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PortfolioInterviewResponse {

    private Long id;
    private String question;
    private String answer;

}
