package start.capstone2.githubexam;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GithubApiController {

    private final GithubApiClient client;

    @GetMapping("/github/{owner}/{repo}")
    public List<String> pullCode(@PathVariable  String owner, @PathVariable String repo) {
        return client.pullCode(owner, repo);
    }
}
