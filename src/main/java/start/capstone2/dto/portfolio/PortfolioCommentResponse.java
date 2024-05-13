package start.capstone2.dto.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PortfolioCommentResponse {
    private Long id;
    private String content;
}
