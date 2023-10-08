package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            if (update.message().text().startsWith("/")) {
                switch (update.message().text()) {
                    case "/start": startBot(update);
                        break;
                    default: unknown(update);
                        break;
                }
            } else {
                allCommands(update);
            }
            logger.info("Processing update: {}", update);
            // Process your updates here
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    //обработка метода /start
    private void startBot(Update update) {
        Long chatId = update.message().chat().id();
        String username = update.message().chat().username();
        String messageText = "Привет, " + username + "! Добро пожаловать в телеграм-бот!";
        SendMessage message = new SendMessage(chatId, messageText);
        //отправляем сообщение
        telegramBot.execute(message);
    }

    //обработка неизвестной команды
    private void unknown(Update update) {
        Long chatId = update.message().chat().id();
        String messageText = "Я пока не знаю такую команду";
        SendMessage message = new SendMessage(chatId, messageText);
        //отправляем сообщение
        telegramBot.execute(message);
        allCommands(update);
    }

    //перечень всех команд
    private void allCommands(Update update) {
        Long chatId = update.message().chat().id();
        String messageText = "Введите команду из списка ниже:" + "\n" +
                "/start";
        SendMessage message = new SendMessage(chatId, messageText);
        //отправляем сообщение
        telegramBot.execute(message);
    }
}
