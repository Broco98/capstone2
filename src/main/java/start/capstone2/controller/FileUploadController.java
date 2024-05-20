package start.capstone2.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import start.capstone2.domain.Image.S3Store;

import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileUploadController {

    private final S3Store s3Store;

    @PostMapping("/upload")
    public String saveFile(@RequestParam MultipartFile file, HttpServletRequest request) throws IOException {

        s3Store.saveImage(file);

        return "ok";
    }

}
