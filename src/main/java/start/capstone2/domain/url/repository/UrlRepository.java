package start.capstone2.domain.url.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.capstone2.domain.url.Url;

public interface UrlRepository extends JpaRepository<Url, Long> {
}
