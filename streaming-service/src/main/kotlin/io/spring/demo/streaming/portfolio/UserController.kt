package io.spring.demo.streaming.portfolio;

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(val userRepository: UserRepository) {

	@GetMapping("/users")
	fun fetchAll() = userRepository.findAll()

	@GetMapping("/users/{github}")
	fun fetchUser(@PathVariable github: String) = userRepository.findUserByGithub(github)

}
