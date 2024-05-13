package start.capstone2.dto.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;
import start.capstone2.domain.techstack.TechStack;

@Data
@AllArgsConstructor
public class PortfolioTechStackRequest {
    private Long techStackId;
}
