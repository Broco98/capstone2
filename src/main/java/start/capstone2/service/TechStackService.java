package start.capstone2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.Image.Image;
import start.capstone2.domain.Image.ImageStore;
import start.capstone2.domain.Image.S3Store;
import start.capstone2.domain.portfolio.PortfolioTechStack;
import start.capstone2.domain.portfolio.repository.PortfolioTechStackRepository;
import start.capstone2.domain.techstack.TechStack;
import start.capstone2.domain.techstack.repository.TechStackRepository;
import start.capstone2.dto.TechStackRequest;
import start.capstone2.dto.TechStackResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TechStackService {

    private final TechStackRepository techStackRepository;
    private final S3Store store;

    @Transactional
    public Long createTechStack(TechStackRequest request) {
        Image image = store.saveImage(request.getImage());
        TechStack techStack = TechStack.createTechStack(request.getName(), image);
        techStackRepository.save(techStack);
        return techStack.getId();
    }

    public List<TechStackResponse> findAllWithImage() {
        List<TechStack> techStacks = techStackRepository.findAllWithImage();
        List<TechStackResponse> results = new ArrayList<>();
        for (TechStack techStack : techStacks) {
            results.add(new TechStackResponse(techStack.getId(), techStack.getName(), techStack.getImage().getSavedName()));
        }
        return results;
    }
}
