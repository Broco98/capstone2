package start.capstone2.dto.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PortfolioScheduleResponse {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String explain;
}
