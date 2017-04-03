package io.spring.demo.quotes;

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class QuoteStreamApplication

fun main(args: Array<String>) {
	SpringApplication.run(QuoteStreamApplication::class.java, *args)
}