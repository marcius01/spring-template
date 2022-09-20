package com.skullprogrammer.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SkullProgrammerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkullProgrammerApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder () {
		return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
	}

}
