package start.capstone2.dto.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PortfolioTechStackRequest {
    private List<String> techStacks;
}
