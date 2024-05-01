package start.capstone2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.Image.Image;
import start.capstone2.domain.Image.dto.ImageRequest;
import start.capstone2.domain.Image.repository.ImageRepository;
import start.capstone2.domain.file.ImageStore;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {

    private ImageRepository imageRepository;
    private ImageStore imageStore;

//    @Transactional
//    public void saveImages(ImageRequest imageRequest) throws IOException {
//        List<Image> images = imageStore.saveImages(imageRequest.getImages());
//        imageRepository.saveAll(images);
//    }

}
