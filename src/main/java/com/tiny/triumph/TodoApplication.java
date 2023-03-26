package com.tiny.triumph;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;

@SpringBootApplication
public class TodoApplication {

	public static void main(String[] args) {
		System.out.println("Spring Version" + SpringVersion.getVersion());
		SpringApplication.run(TodoApplication.class, args);
	}

}
