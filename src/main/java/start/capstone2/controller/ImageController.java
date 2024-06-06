package start.capstone2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import start.capstone2.domain.Image.S3Store;

import java.io.InputStream;


@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {

    private final S3Store store;

    @GetMapping("/{imageName}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String imageName) {
        // 이미지 파일의 InputStream을 가져옵니다.
        InputStream inputStream = store.findImageBytes(imageName);
        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

        // 파일 이름을 기반으로 MIME 타입을 결정합니다.
        String contentType = getContentType(imageName);

        // MIME 타입이 유효하지 않으면 오류 응답을 반환합니다.
        if (contentType == null) {
            return ResponseEntity.badRequest().body(null);
        }

        // 이미지 데이터를 응답으로 반환합니다.
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(inputStreamResource);
    }

    private String getContentType(String imageName) {
        // 파일 이름에서 확장자를 추출하고 적절한 MIME 타입을 반환합니다.
        if (imageName.endsWith(".jpg") || imageName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (imageName.endsWith(".png")) {
            return "image/png";
        } else if (imageName.endsWith(".gif")) {
            return "image/gif";
        }
        // 필요에 따라 더 많은 파일 타입을 추가할 수 있습니다.
        return null; // 지원되지 않는 파일 형식인 경우
    }
}
