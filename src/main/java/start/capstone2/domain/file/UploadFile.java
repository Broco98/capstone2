package start.capstone2.domain.file;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadFile {

    @Id
    @GeneratedValue
    @Column(name = "upload_file_id")
    private Long id;

    private String uploadName;
    private String savedName;

    public static UploadFile createUploadFile(String uploadName, String savedName) {
        UploadFile file = new UploadFile();
        file.uploadName = uploadName;
        file.savedName = savedName;

        return file;
    }
}
