package start.capstone2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import start.capstone2.config.S3Config;

@SpringBootApplication
@EnableJpaAuditing
public class Capstone2Application {

	public static void main(String[] args) {
		SpringApplication.run(Capstone2Application.class, args);
	}
}
