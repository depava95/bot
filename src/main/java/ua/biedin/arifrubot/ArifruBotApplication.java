package ua.biedin.arifrubot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import ua.biedin.arifrubot.handler.BotHandler;

@SpringBootApplication
public class ArifruBotApplication {

    public static void main(String[] args) throws TelegramApiRequestException {
        SpringApplication.run(ArifruBotApplication.class, args);
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        telegramBotsApi.registerBot(new BotHandler());
    }
}
