package com.clipboarder.clipboarder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ClipboarderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClipboarderApplication.class, args);
	}

}
