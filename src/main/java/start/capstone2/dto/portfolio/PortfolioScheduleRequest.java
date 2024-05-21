package start.capstone2.dto.portfolio;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PortfolioScheduleRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
}
