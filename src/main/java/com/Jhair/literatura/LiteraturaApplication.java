package com.Jhair.literatura;
import com.Jhair.literatura.application.Menu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LiteraturaApplication {

	public static void main(String[] args) {

		SpringApplication.run(LiteraturaApplication.class, args);
	}
	@Bean
	CommandLineRunner run(Menu menu) {
		return args -> menu.start();
	}
}
