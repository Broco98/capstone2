package start.capstone2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import start.capstone2.domain.Image.ImageStore;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {

    @GetMapping("/{imageName}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String imageName) {
        try {
            Resource resource = new UrlResource("file:" + ImageStore.getFullPath(imageName));

            if (!resource.exists()) {
                throw new FileNotFoundException("파일이 존재하지 않습니다.");
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(resource.getFilename()))
                    .body(resource);

        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("파일이 존재하지 않습니다.");
        }
    }

}
