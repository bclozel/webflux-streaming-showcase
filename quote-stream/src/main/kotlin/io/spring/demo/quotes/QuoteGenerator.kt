package io.spring.demo.quotes

import java.math.BigDecimal
import java.math.MathContext
import java.time.Duration
import java.util.Random

import reactor.core.publisher.Flux
import reactor.core.publisher.SynchronousSink

import org.springframework.stereotype.Component
import java.time.Instant

@Component
class QuoteGenerator {

	val mathContext = MathContext(2)

	val random = Random()

	val prices = listOf(
			Quote("CTXS", BigDecimal(82.26, mathContext)),
			Quote("DELL", BigDecimal(63.74, mathContext)),
			Quote("GOOG", BigDecimal(847.24, mathContext)),
			Quote("MSFT", BigDecimal(65.11, mathContext)),
			Quote("ORCL", BigDecimal(45.71, mathContext)),
			Quote("RHT", BigDecimal(84.29, mathContext)),
			Quote("VMW", BigDecimal(92.21, mathContext))
	)


	fun fetchQuoteStream(period: Duration) = Flux.generate( { 0 } , { index, sink: SynchronousSink<Quote> ->
					sink.next(updateQuote(prices[index]))
					(index + 1) % prices.size
				})
				.zipWith(Flux.interval(period))
				.map { it.t1.copy(instant = Instant.now()) }
				.share()
				.log()


	private fun updateQuote(quote: Quote) = quote.copy(
			price = quote.price.add(quote.price.multiply(BigDecimal(0.05 * random.nextDouble()), mathContext))
	)

}
