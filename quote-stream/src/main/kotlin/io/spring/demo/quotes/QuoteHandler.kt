package io.spring.demo.quotes;

import org.springframework.http.MediaType.APPLICATION_STREAM_JSON
import org.springframework.http.MediaType.TEXT_EVENT_STREAM
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.ok
import java.time.Duration.ofMillis


@Component
class QuoteHandler(val quoteGenerator: QuoteGenerator) {

	fun fetchQuotesSSE(req: ServerRequest) = ok()
				.contentType(TEXT_EVENT_STREAM)
				.body(quoteGenerator.fetchQuoteStream(ofMillis(200)), Quote::class.java)

	fun fetchQuotes(req: ServerRequest) = ok()
				.contentType(APPLICATION_STREAM_JSON)
				.body(quoteGenerator.fetchQuoteStream(ofMillis(200)), Quote::class.java)

}
