package io.spring.demo.streaming.portfolio;

import reactor.core.publisher.Mono;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

	Mono<User> findUserByGithub(String github);
}
