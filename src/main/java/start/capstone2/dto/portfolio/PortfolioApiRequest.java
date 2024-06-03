package start.capstone2.dto.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;
import start.capstone2.domain.portfolio.Method;

@Data
@AllArgsConstructor
public class PortfolioApiRequest {
    private String name;
}
