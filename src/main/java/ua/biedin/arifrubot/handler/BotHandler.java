package ua.biedin.arifrubot.handler;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.biedin.arifrubot.service.BotService;

import java.util.Arrays;

@Service
public class BotHandler extends TelegramLongPollingBot {

    private final BotService botService;
    @Value("${token}")
    private String TOKEN;
    @Value("${bot_name}")
    private String BOT_NAME;
    @Value("${get_information}")
    private String GET_INFORMATION;
    private  final int START_STEP = 0;
    private final  int MENU_STEP = 1;
    private final int SEARCH_STEP = 2;

    public BotHandler(BotService botService) {
        super();
        this.botService = botService;
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
        final int step = botService.getStep(message.getFrom().getId(), message.getChatId());
        SendMessage sendMessage = new SendMessage();
        switch (step) {
            case START_STEP:
                sendMessage = start(message);
                break;
            case MENU_STEP:
                sendMessage = main(message);
                break;
        }
        execute(sendMessage);
    }

    private SendMessage main(Message message) {
        if(message.hasText()) {
            if(message.getText().contains(GET_INFORMATION)) {
                return getInformationAboutWorkerMessage(message);
            } else {
                return getWrongMessage(message);
            }
        }
        return getWrongMessage(message);
    }

    private SendMessage getInformationAboutWorkerMessage(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Обери за яким параметром шукати \uD83D\uDD0E");
        sendMessage.setReplyMarkup(getSearchKeyboard());
        botService.createStep(message.getFrom().getId(), message.getChatId(), SEARCH_STEP);
        return sendMessage;
    }

    private ReplyKeyboard getSearchKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        keyboardRow1.add("Ім'я");
        keyboardRow1.add("Фамілія");
        keyboardRow2.add("Номер телефона");
        keyboardRow2.add("Посада");
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setOneTimeKeyboard(false);
        keyboardMarkup.setKeyboard(Arrays.asList(keyboardRow1, keyboardRow2));
        return keyboardMarkup;
    }

    private SendMessage getWrongMessage(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Я такої команди не розумію \uD83D\uDE35");
        sendMessage.setChatId(message.getChatId());
        sendMessage.setReplyToMessageId(message.getMessageId());
        return sendMessage;
    }

    private SendMessage start(Message message) {
        ReplyKeyboardMarkup mainKeyboard = getMainMenuKeyboard();
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setReplyMarkup(mainKeyboard);
        sendMessage.setText("**Обери будь ласка дію, яку ти хочеш виконати** \uD83D\uDE4F");
        botService.createStep(message.getFrom().getId(), message.getChatId(), MENU_STEP);
        return sendMessage;
    }

    private ReplyKeyboardMarkup getMainMenuKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);
        KeyboardRow row = new KeyboardRow();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        row.add("Отримати інформацію щодо співробітника \uD83D\uDCC1");
        row.add("Додати нового співробітника \uD83C\uDD95");
        row1.add("Змінити контакти існуючого ✏");
        row1.add("Отримувати повідомлення про погоду \uD83C\uDF29");
        keyboardMarkup.setKeyboard(Arrays.asList(row, row1, row2, row3));

        return keyboardMarkup;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }
}
