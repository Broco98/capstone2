package start.capstone2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import start.capstone2.domain.Image.ImageStore;
import java.net.MalformedURLException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {

    private final ImageStore imageStore;

    @GetMapping("/{imageName}")
    public Resource downloadImage(@PathVariable String imageName) throws MalformedURLException {
        return new UrlResource("file:" + imageStore.getFullPath(imageName));
    }

}
