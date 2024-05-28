package start.capstone2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.UrlRequest;
import start.capstone2.dto.portfolio.PortfolioResponse;
import start.capstone2.service.UrlService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/url")
public class UrlController {

    private final UrlService urlService;

    @PostMapping("")
    public Long createUrl(Long userId, UrlRequest request) {
        return urlService.createUrl(userId, request);
    }

    @PutMapping("/{urlId}")
    public void updateUrl(Long userId, @PathVariable Long urlId, UrlRequest request) {
        urlService.createUrl(userId, request);
    }

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
