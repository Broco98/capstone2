package start.capstone2.domain.Image;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import start.capstone2.domain.Image.Image;
import start.capstone2.domain.Image.ImageType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ImageStore {

    // 저장 위치
    @Value("${image.dir}")
    private static String imageDir; // static 가능

    // 저장 위치 + 파일 이름
    public static String getFullPath(String fileName) {
        return imageDir + fileName;
    }

    public Image saveImage(MultipartFile multipartFile){
        if (multipartFile.isEmpty())
            return null;

        String originalImageName = multipartFile.getOriginalFilename();
        String saveImageName = createSaveFileName(originalImageName);

        try {
            multipartFile.transferTo(new File(getFullPath(saveImageName)));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        if (originalImageName == null) {
            return Image.createImage(saveImageName, saveImageName);
        }
        return Image.createImage(originalImageName, saveImageName);
    }

    private String createSaveFileName(String originalImageName) {

        // image 이름이 없는 경우
        if (originalImageName == null) {
            String ext = "png";
            String uuid = UUID.randomUUID().toString();
            return uuid + "." + ext;
        }

        int pos = originalImageName.lastIndexOf(".");
        String ext = originalImageName.substring(pos+1);

        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    public static void removeImage(Image image) {
        File file = new File(getFullPath(image.getSavedName()));
        if (file.exists()) {
            file.delete(); // deleteOnExit 은 지연 삭제
        }
    }
}
