package start.capstone2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class TechStackResponse {
    private Long id;
    private String name;
    private String imageUrl;
}
