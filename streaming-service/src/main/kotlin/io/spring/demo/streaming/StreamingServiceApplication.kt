package io.spring.demo.streaming;

import io.spring.demo.streaming.portfolio.User
import io.spring.demo.streaming.portfolio.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import java.time.Duration.ofSeconds

@SpringBootApplication
class StreamingServiceApplication {

	@Bean
	fun createUsers(userRepository: UserRepository) = CommandLineRunner {
		val users = listOf(
				User("sdeleuze", "Sebastien Deleuze"),
				User("snicoll", "Stephane Nicoll"),
				User("rstoyanchev", "Rossen Stoyanchev"),
				User("smaldini", "Stephane Maldini"),
				User("simonbasle", "Simon Basle"),
				User("bclozel", "Brian Clozel")
		)
		userRepository.saveAll(users).blockLast(ofSeconds(3))
	}

}

fun main(args: Array<String>) {
	SpringApplication.run(StreamingServiceApplication::class.java, *args)
}
