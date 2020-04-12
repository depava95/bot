package ua.biedin.arifrubot.handler;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;

public class BotHandler extends TelegramLongPollingBot {

    private static String token = "1124691664:AAEZ1tKlNsSWGrNyGqaEMHlKIMZwfXRRYnQ";

    private String botName = "1124691664:AAEZ1tKlNsSWGrNyGqaEMHlKIMZwfXRRYnQ";

    public BotHandler() {
        super();
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) {
            Message message = update.getMessage();
            if(message.hasText() || message.hasLocation()) {
                handleIncomingMessage(message);
            }
        }
    }

    private void handleIncomingMessage(Message message) throws TelegramApiException {
        execute(start(message));
    }

    private SendMessage start(Message message) {
        ReplyKeyboardMarkup mainKeyboard = getMainKeyboard();
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setReplyMarkup(mainKeyboard);
        sendMessage.setText("Ну че петух, раздвигай булки");
        return sendMessage;
    }

    private ReplyKeyboardMarkup getMainKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        KeyboardRow row = new KeyboardRow();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        row.add("Попытка создания кнопки один");
        row1.add("Попытка создания кнопки два");
        row2.add("Три");

        keyboardMarkup.setKeyboard(Arrays.asList(row, row1, row2));

        return keyboardMarkup;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
