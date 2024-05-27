package start.capstone2.dto.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;
import start.capstone2.domain.techstack.TechStack;

import java.util.List;

@Data
@AllArgsConstructor
public class PortfolioTechStackRequest {
    private List<Long> techStackId;
}
