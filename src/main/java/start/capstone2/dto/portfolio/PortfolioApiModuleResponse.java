package start.capstone2.dto.portfolio;

import lombok.*;
import start.capstone2.domain.portfolio.Method;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PortfolioApiModuleResponse {
    private Long id;
    private Method method;
    private String url;
    private String description;
    private String response;
}
