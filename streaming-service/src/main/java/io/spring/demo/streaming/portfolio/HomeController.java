package io.spring.demo.streaming.portfolio;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;

@Controller
public class HomeController {

    private final UserRepository userRepository;

    private final Flux<Quote> quoteStream;

    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.quoteStream = WebClient.create("http://localhost:8081")
                                    .get()
                                    .uri("/quotes")
                                    .accept(APPLICATION_STREAM_JSON)
                                    .retrieve()
                                    .bodyToFlux(Quote.class)
                                    .share()
                                    .log();
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("users", this.userRepository.findAll());
        return "index";
    }

    @GetMapping(path = "/quotes/feed", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<Quote> fetchQuotesStream() {
        return this.quoteStream;
    }

    @GetMapping("/quotes")
    public String quotes() {
        return "quotes";
    }
}
