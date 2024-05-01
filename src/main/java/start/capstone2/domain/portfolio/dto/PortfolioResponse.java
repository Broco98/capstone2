package start.capstone2.domain.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import start.capstone2.domain.user.Group;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class PortfolioResponse {

    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer contribution;
    private String purpose;
    private String content;
    private Integer memberNum;

//    private MultipartFile cardImage;
//    private List<MultipartFile> images = new ArrayList<>();

    private String cardImageName;
    private List<String> imageNames = new ArrayList<>();
//    private List<Long> memberIds = new ArrayList<>();
//    private List<String> memberNames = new ArrayList<>();

    private List<String> sharedGroupNames = new ArrayList<>();

}
