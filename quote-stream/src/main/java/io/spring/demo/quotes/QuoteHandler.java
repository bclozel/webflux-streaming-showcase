package io.spring.demo.quotes;

import reactor.core.publisher.Mono;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import static java.time.Duration.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
public class QuoteHandler {

	private final QuoteGenerator quoteGenerator;

	public QuoteHandler(QuoteGenerator quoteGenerator) {
		this.quoteGenerator = quoteGenerator;
	}

	@Bean
	public RouterFunction<ServerResponse> route() {
		return RouterFunctions
				.route(GET("/quotes").and(accept(TEXT_EVENT_STREAM)), this::fetchQuotesSSE)
				.andRoute(GET("/quotes").and(accept(APPLICATION_STREAM_JSON)), this::fetchQuotes);
	}

	private Mono<ServerResponse> fetchQuotesSSE(ServerRequest request) {
		return ok()
				.contentType(TEXT_EVENT_STREAM)
				.body(this.quoteGenerator.fetchQuoteStream(ofMillis(200)), Quote.class);
	}

	private Mono<ServerResponse> fetchQuotes(ServerRequest request) {
		return ok()
				.contentType(APPLICATION_STREAM_JSON)
				.body(this.quoteGenerator.fetchQuoteStream(ofMillis(200)), Quote.class);
	}

}
