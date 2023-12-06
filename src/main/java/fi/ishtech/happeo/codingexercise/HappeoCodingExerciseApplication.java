package fi.ishtech.happeo.codingexercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = { UserDetailsServiceAutoConfiguration.class })
public class HappeoCodingExerciseApplication {

	public static void main(String[] args) {
		SpringApplication.run(HappeoCodingExerciseApplication.class, args);
	}

}
