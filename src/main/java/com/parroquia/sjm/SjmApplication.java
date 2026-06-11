package com.parroquia.sjm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // <-- Habilita tareas programadas
public class SjmApplication {

	public static void main(String[] args) {
		SpringApplication.run(SjmApplication.class, args);
	}

}