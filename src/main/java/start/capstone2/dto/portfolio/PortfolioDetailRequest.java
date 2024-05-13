package start.capstone2.dto.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import start.capstone2.domain.portfolio.ShareStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class PortfolioDetailRequest {
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer contribution;
    private String purpose;
    private String content;
    private Integer teamNum;
    private MultipartFile cardImage;
    private ShareStatus status;
}
