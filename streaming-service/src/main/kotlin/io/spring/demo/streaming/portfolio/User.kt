package io.spring.demo.streaming.portfolio

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User(val github: String, val name: String, @Id val id: String? = null)
