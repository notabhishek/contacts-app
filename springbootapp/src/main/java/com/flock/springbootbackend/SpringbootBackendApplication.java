package com.flock.springbootbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootBackendApplication {
	public static void main(String[] args) {
		System.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
		SpringApplication.run(SpringbootBackendApplication.class, args);
	}
}
