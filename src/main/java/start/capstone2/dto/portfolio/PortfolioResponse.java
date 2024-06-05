package start.capstone2.dto.portfolio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import start.capstone2.domain.portfolio.ShareStatus;

import java.time.LocalDate;

@JsonInclude
@Data
@AllArgsConstructor
public class PortfolioResponse {

    private Long id;
    private String imageUrl;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer teamNum;
    private String description;
    private String contribution;
    private String techStacks;
    private ShareStatus status;

}
