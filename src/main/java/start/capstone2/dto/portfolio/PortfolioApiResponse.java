package start.capstone2.dto.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;
import start.capstone2.domain.portfolio.Method;

@Data
@AllArgsConstructor
public class PortfolioApiResponse {
    private Long id;
    private Method method;
    private String url;
    private String explain;
    private String response;
}
