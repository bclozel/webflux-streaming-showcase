package io.spring.demo.quotes;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import static java.time.Duration.ofMillis;
import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class QuoteHandler {

	private final Flux<Quote> quoteStream;

	public QuoteHandler(QuoteGenerator quoteGenerator) {
		this.quoteStream = quoteGenerator.fetchQuoteStream(ofMillis(200));
	}

	public Mono<ServerResponse> fetchQuotesSSE(ServerRequest request) {
		return ok()
				.contentType(TEXT_EVENT_STREAM)
				.body(this.quoteStream, Quote.class);
	}

	public Mono<ServerResponse> fetchQuotes(ServerRequest request) {
		return ok()
				.contentType(APPLICATION_STREAM_JSON)
				.body(this.quoteStream, Quote.class);
	}

}
