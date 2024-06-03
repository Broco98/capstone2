package start.capstone2.domain.Image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import start.capstone2.domain.Image.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

}
