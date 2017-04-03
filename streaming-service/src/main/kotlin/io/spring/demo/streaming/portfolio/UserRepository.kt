package io.spring.demo.streaming.portfolio;

import reactor.core.publisher.Mono;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

interface UserRepository : ReactiveMongoRepository<User, String> {

	 fun findUserByGithub(github: String): Mono<User>
}
