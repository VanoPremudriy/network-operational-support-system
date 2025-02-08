package ru.mirea.network.operational.support.system.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class NetworkOperationalSupportSystemConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetworkOperationalSupportSystemConfigApplication.class, args);
	}

}
