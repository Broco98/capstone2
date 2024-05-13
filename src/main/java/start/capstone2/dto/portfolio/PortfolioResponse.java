package start.capstone2.dto.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;
import start.capstone2.domain.portfolio.ShareStatus;

@Data
@AllArgsConstructor
public class PortfolioResponse {
    private Long id;
    private String imageUrl;
    private ShareStatus status;
}
