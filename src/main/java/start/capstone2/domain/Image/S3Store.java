package start.capstone2.domain.Image;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Store {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String getFullName(String fileName) {
        return "image/" + fileName;
    }

    public String saveImage(MultipartFile multipartFile) {

        String originalName = multipartFile.getOriginalFilename();
        String saveName = createSaveFileName(originalName);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        try {
            PutObjectRequest request = new PutObjectRequest(bucket, getFullName(saveName), multipartFile.getInputStream(), metadata);
            amazonS3.putObject(request);
        } catch (IOException e) {
            throw new IllegalStateException("파일이 존재하지 않습니다");
        }

        return saveName;
    }

    private String createSaveFileName(String originalName) {

        if (originalName == null) {
            throw new IllegalStateException("파일 이름이 존재하지 않습니다.");
        }

        int pos = originalName.lastIndexOf(".");
        String ext = originalName.substring(pos+1);

        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    public void removeImage(String saveName) {
        DeleteObjectRequest request = new DeleteObjectRequest(bucket, getFullName(saveName));
        amazonS3.deleteObject(request);
    }

}
