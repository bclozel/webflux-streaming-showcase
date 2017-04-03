package io.spring.demo.quotes

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_STREAM_JSON
import org.springframework.http.MediaType.TEXT_EVENT_STREAM
import org.springframework.web.reactive.function.server.router

@Configuration
class QuoteRoutes(val quoteHandler: QuoteHandler) {

    @Bean
    fun quoteRouter() = router {
        GET("/quotes").nest {
            accept(TEXT_EVENT_STREAM, quoteHandler::fetchQuotesSSE)
            accept(APPLICATION_STREAM_JSON, quoteHandler::fetchQuotes)
        }
    }

}