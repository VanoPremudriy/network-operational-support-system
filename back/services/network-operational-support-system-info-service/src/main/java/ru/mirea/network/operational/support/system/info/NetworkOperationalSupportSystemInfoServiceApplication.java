package ru.mirea.network.operational.support.system.info;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class NetworkOperationalSupportSystemInfoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetworkOperationalSupportSystemInfoServiceApplication.class, args);
    }

}
