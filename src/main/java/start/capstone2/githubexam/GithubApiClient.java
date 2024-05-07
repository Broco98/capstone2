package start.capstone2.githubexam;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GithubApiClient {

    private static final String GITHUB_API_URL = "https://api.github.com/repos/{owner}/{repo}/pulls";

    private final WebClient client;

    private final ObjectMapper mapper;

    public GithubApiClient(WebClient.Builder builder, ObjectMapper mapper) {
        client = builder.baseUrl("https://api.github.com").build();
        this.mapper = mapper;
    }

    public List<String> pullCode(String owner, String repo) {
        String url = GITHUB_API_URL.replace("{owner}", owner)
                .replace("{repo}", repo);

        Mono<Object[]> response = client.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Object[].class).log();

        Object[] objects = response.block();

        return Arrays.stream(objects)
                .map(object -> mapper.convertValue(object, String.class))
                .collect(Collectors.toList());
    }
}
