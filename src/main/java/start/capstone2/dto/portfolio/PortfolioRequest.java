package start.capstone2.dto.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import start.capstone2.domain.portfolio.ShareStatus;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PortfolioRequest {

    private MultipartFile image;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer teamNum;
    private String description;
    private Integer contribution;
    private ShareStatus status;

}
