package com.tiny.triumph;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost:3000")
public class TodoApplication {

	public static void main(String[] args) {
		System.out.println("Spring Version" + SpringVersion.getVersion());
		SpringApplication.run(TodoApplication.class, args);
	}

}
