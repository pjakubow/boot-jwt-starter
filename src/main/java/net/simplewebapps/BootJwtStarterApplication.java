package net.simplewebapps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class BootJwtStarterApplication {
	public static void main(String[] args) {
		SpringApplication.run(BootJwtStarterApplication.class, args);
	}
}
