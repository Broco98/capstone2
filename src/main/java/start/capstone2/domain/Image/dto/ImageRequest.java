package start.capstone2.domain.Image.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class ImageRequest {

    private List<MultipartFile> images = new ArrayList<>();

}
