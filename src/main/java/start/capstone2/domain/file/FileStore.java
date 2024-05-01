package start.capstone2.domain.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    // 저장 위치
    @Value("${file.dir}")
    private String fileDir;

    // 저장 위치 + 파일 이름
    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    public List<UploadFile> saveFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> files = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                files.add(saveFile(multipartFile));
            }
        }
        return files;
    }

    public UploadFile saveFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty())
            return null;

        String originalFileName = multipartFile.getOriginalFilename();
        String saveFileName = createSaveFileName(originalFileName);
        multipartFile.transferTo(new File(getFullPath(saveFileName)));

        if (originalFileName == null) {
            return UploadFile.createUploadFile(saveFileName, saveFileName);
        }
        return UploadFile.createUploadFile(originalFileName, saveFileName);
    }

    private String createSaveFileName(String originalFileName) {
    
        // file 이름이 없는 경우
        if (originalFileName == null) {
            String ext = "png";
            String uuid = UUID.randomUUID().toString();
            return uuid + "." + ext;
        }

        int pos = originalFileName.lastIndexOf(".");
        String ext = originalFileName.substring(pos+1);

        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }
}
