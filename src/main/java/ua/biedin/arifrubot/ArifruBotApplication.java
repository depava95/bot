package ua.biedin.arifrubot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class ArifruBotApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(ArifruBotApplication.class, args);
    }
}
