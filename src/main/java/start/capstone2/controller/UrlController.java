package start.capstone2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.UrlRequest;
import start.capstone2.service.UrlService;

@Tag(name = "Url api", description = "공유를 위한 url 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/url")
public class UrlController {
    
    private final UrlService urlService;

    @Operation(summary = "create url", description = "공유를 위한 랜덤 url 생성")
    @PostMapping("")
    public Long createUrl(Long userId, UrlRequest request) {
        return urlService.createUrl(userId, request);
    }
    
    @Operation(summary = "delete url", description = "url 삭제")
    @DeleteMapping("/{urlId}")
    public void deleteUrl(Long userId, @PathVariable Long urlId) {
        urlService.deleteUrl(userId, urlId);
    }

//    @GetMapping("/{urlId}/portfolio")
//    public ResponseResult<List<PortfolioResponse>> findAllPortfolioInUrl(Long userId, @PathVariable Long urlId) {
//        List<PortfolioResponse> result = urlService.findAllById(userId, urlId);
//        return new ResponseResult<>(result);
//    }
}
