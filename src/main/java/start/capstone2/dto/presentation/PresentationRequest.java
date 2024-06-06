package start.capstone2.dto.presentation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PresentationRequest {
    private String image;
    private String title;
    private String startDate;
    private String endDate;
    private int teamNum;
    private String description;
    private String contribution;
    private String techStack;
}
