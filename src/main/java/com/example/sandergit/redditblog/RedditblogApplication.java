package com.example.sandergit.redditblog;

import com.example.sandergit.redditblog.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfig.class)
public class RedditblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedditblogApplication.class, args);
	}

}
