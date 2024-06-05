package start.capstone2.dto.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;
import start.capstone2.domain.portfolio.Method;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class PortfolioApiResponse {
    private Long id;
    private String name;
    List<PortfolioApiModuleResponse> modules;
}
