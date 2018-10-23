package com.benjamintanone.aboutapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AboutApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AboutApiApplication.class, args);
	}
}
