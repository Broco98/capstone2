package start.capstone2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class TechStackRequest {
    private String name;
    private MultipartFile image;
}
