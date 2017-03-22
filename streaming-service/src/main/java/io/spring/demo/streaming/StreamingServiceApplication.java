package io.spring.demo.streaming;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import io.spring.demo.streaming.portfolio.User;
import io.spring.demo.streaming.portfolio.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StreamingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamingServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner createUsers(UserRepository userRepository) {
		return strings -> {
			List<User> users = Arrays.asList(
					new User("sdeleuze", "Sebastien Deleuze"),
					new User("snicoll", "Stephane Nicoll"),
					new User("rstoyanchev", "Rossen Stoyanchev"),
					new User("smaldini", "Stephane Maldini"),
					new User("simonbasle", "Simon Basle"),
					new User("bclozel", "Brian Clozel")
			);
			userRepository.saveAll(users).blockLast(Duration.ofSeconds(3));
		};
	}

}
