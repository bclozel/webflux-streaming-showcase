package io.spring.demo.streaming.portfolio;


import org.springframework.http.MediaType.APPLICATION_STREAM_JSON
import org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux

@Controller
class HomeController(val userRepository: UserRepository) {

	@GetMapping("/")
	fun home(model: Model): String {
		model["users"] = userRepository.findAll()
		return "index"
	}

	@GetMapping("/quotes/feed", produces = arrayOf(TEXT_EVENT_STREAM_VALUE))
	@ResponseBody
	fun fetchQuotesStream() = WebClient.create("http://localhost:8081")
			.get()
			.uri("/quotes")
			.accept(APPLICATION_STREAM_JSON)
			.retrieve()
			.bodyToFlux<Quote>()
			.share()
			.log()

	@GetMapping("/quotes")
	fun quotes() = "quotes"

}
