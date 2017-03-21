package io.spring.demo.quotes;

import java.time.Duration;

import reactor.core.publisher.Mono;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Component
public class QuoteHandler {

	private final QuoteGenerator quoteGenerator;

	public QuoteHandler(QuoteGenerator quoteGenerator) {
		this.quoteGenerator = quoteGenerator;
	}

	@Bean
	public RouterFunction<ServerResponse> route() {
		return RouterFunctions
				.route(GET("/quotes").and(accept(MediaType.TEXT_EVENT_STREAM)), this::fetchQuotesSSE)
				.andRoute(GET("/quotes").and(accept(MediaType.APPLICATION_STREAM_JSON)), this::fetchQuotes);
	}

	private Mono<ServerResponse> fetchQuotesSSE(ServerRequest request) {
		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
				.body(this.quoteGenerator.fetchQuoteStream(Duration.ofMillis(200)), Quote.class);
	}

	private Mono<ServerResponse> fetchQuotes(ServerRequest request) {
		return ServerResponse.ok().contentType(MediaType.APPLICATION_STREAM_JSON)
				.body(this.quoteGenerator.fetchQuoteStream(Duration.ofMillis(200)), Quote.class);
	}

}
