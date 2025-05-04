package ru.mirea.network.operational.support.system.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@EnableFeignClients
@SpringBootApplication
public class NetworkOperationalSupportSystemBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetworkOperationalSupportSystemBackApplication.class, args);
    }

}
