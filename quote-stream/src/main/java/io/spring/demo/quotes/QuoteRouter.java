package io.spring.demo.quotes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.Rendering;

import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class QuoteRouter {

	@Bean
	public RouterFunction<ServerResponse> route(QuoteHandler quoteHandler) {
		return RouterFunctions
				.route(GET("/").and(accept(TEXT_EVENT_STREAM)), quoteHandler::fetchQuotesSSE)
				.andRoute(GET("/quotes").and(accept(TEXT_EVENT_STREAM)), quoteHandler::fetchQuotesSSE)
				.andRoute(GET("/quotes").and(accept(APPLICATION_STREAM_JSON)), quoteHandler::fetchQuotes);
	}
}
