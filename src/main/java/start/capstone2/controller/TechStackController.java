package start.capstone2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.TechStackRequest;
import start.capstone2.dto.TechStackResponse;
import start.capstone2.service.TechStackService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tech-stack")
public class TechStackController {

    private final TechStackService techStackService;

    @GetMapping("")
    public List<TechStackResponse> findAllTechStack() {
        return techStackService.findAllWithImage();
    }

    @PostMapping("")
    public Long createTechStack(TechStackRequest techStackRequest) {
        return techStackService.createTechStack(techStackRequest);
    }

}
