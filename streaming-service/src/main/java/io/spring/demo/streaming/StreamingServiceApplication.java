package io.spring.demo.streaming;


import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import com.samskivert.mustache.Mustache;
import io.spring.demo.streaming.portfolio.User;
import io.spring.demo.streaming.portfolio.UserRepository;
import io.spring.demo.streaming.support.MustacheResourceTemplateLoader;
import io.spring.demo.streaming.support.MustacheViewResolver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mustache.MustacheProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StreamingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamingServiceApplication.class, args);
	}

	@Bean
	public MustacheViewResolver mustacheViewResolver(MustacheProperties properties) {
		MustacheViewResolver viewResolver = new MustacheViewResolver();
		viewResolver.setPrefix(properties.getPrefix());
		viewResolver.setSuffix(properties.getSuffix());
		MustacheResourceTemplateLoader loader =
				new MustacheResourceTemplateLoader(properties.getPrefix(), properties.getSuffix());
		viewResolver.setCompiler(Mustache.compiler().escapeHTML(false).withLoader(loader));
		return viewResolver;
	}

	@Bean
	public CommandLineRunner createUsers(UserRepository userRepository) {
		return strings -> {
			List<User> users = Arrays.asList(
					new User("sdeleuze", "Sebastien Deleuze"),
					new User("snicoll", "Stephane Nicoll"),
					new User("rstoyanchev", "Rossen Stoyanchev"),
					new User("smaldini", "Stephane Maldini"),
					new User("sbasle", "Simon Basle"),
					new User("bclozel", "Brian Clozel")
			);
			userRepository.save(users).blockLast(Duration.ofSeconds(3));
		};
	}

}
