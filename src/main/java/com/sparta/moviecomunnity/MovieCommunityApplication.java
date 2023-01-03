package com.sparta.moviecomunnity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MovieCommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieCommunityApplication.class, args);
    }

}
