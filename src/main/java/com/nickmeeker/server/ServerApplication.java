package com.nickmeeker.server;

import com.nickmeeker.server.config.MLConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication implements CommandLineRunner {

	@Autowired
	public MLConfig mlConfig;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ServerApplication.class);
		app.run();
	}

	public void run(String... args) throws Exception {
		System.out.println(mlConfig.getMLConfigValue("input.nodes"));


	}
}
