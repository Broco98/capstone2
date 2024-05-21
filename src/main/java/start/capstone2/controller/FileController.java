package start.capstone2.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import start.capstone2.domain.Image.S3Store;

import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    private final S3Store s3Store;

    @PostMapping("/upload")
    public String saveFile(@RequestParam MultipartFile file, HttpServletRequest request) throws IOException {

        s3Store.saveImage(file);

        return "ok";
    }

    @GetMapping("/{fileName}")
    public Resource getFile(@PathVariable String fileName) {
        return new InputStreamResource(s3Store.findImageBytes(fileName));
    }

}
