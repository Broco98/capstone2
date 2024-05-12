package start.capstone2.dto.image;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class ImageRequest {

    private List<MultipartFile> images = new ArrayList<>();

}
