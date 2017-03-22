package io.spring.demo.streaming.portfolio;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	private final UserRepository userRepository;

	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/users")
	public Flux<User> fetchAll() {
		return this.userRepository.findAll();
	}

	@GetMapping("/users/{github}")
	public Mono<User> fetchUser(@PathVariable String github) {
		return this.userRepository.findUserByGithub(github);
	}
}
