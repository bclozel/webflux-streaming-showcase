package io.spring.demo.streaming;


import com.samskivert.mustache.Mustache;
import io.spring.demo.streaming.support.MustacheResourceTemplateLoader;
import io.spring.demo.streaming.support.MustacheViewResolver;

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

}
