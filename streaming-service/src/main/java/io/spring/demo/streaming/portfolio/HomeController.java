package io.spring.demo.streaming.portfolio;

import reactor.core.publisher.Flux;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
public class HomeController {

	private final UserRepository userRepository;

	public HomeController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("users", this.userRepository.findAll());
		return "index";
	}

	@GetMapping(path = "/quotes/feed", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseBody
	public Flux<Quote> fetchQuotesStream() {
		return WebClient.create("http://localhost:8081")
				.get()
				.uri("/quotes")
				.accept(MediaType.APPLICATION_STREAM_JSON)
				.exchange()
				.flatMap(response -> response.bodyToFlux(Quote.class))
				.share()
				.log();
	}

	@GetMapping("/quotes")
	public String quotes() {
		return "quotes";
	}
}
