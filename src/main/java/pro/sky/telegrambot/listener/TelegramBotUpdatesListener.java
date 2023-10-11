package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repositories.NotificationTaskRepository;
import pro.sky.telegrambot.service.NotificationTaskService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j // logger -> log
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    @Autowired
    private NotificationTaskService notificationTaskService;
    @Autowired
    private NotificationTaskRepository notificationTaskRepository;

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
                    case "/add": informToAddMessage(update);
                        break;
                    default: informThatUnknownTask(update);
                        break;
                }
            } else {
                remindAddMessage(update);
            }
            log.info("Processing update: {}", update);
            // Process your updates here
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    //обработка метода /start
    private void startBot(Update update) {
        Long chatId = update.message().chat().id();
        String username = update.message().chat().firstName();
        String messageText = "Привет, " + username + "! Добро пожаловать в телеграм-бот!";
        SendMessage message = new SendMessage(chatId, messageText);
        //отправляем сообщение
        telegramBot.execute(message);
        informToAddMessage(update);
    }

    //обработка метода /add
    private void informToAddMessage(Update update) {
        Long chatId = update.message().chat().id();
        String messageText = "Введи напоминание в формате - 01.01.2023 20:00 Напоминение";
        SendMessage message = new SendMessage(chatId, messageText);
        //отправляем сообщение
        telegramBot.execute(message);
    }

    //обработка неизвестной команды
    private void informThatUnknownTask(Update update) {
        Long chatId = update.message().chat().id();
        String messageText = "Я пока не знаю такую команду";
        SendMessage message = new SendMessage(chatId, messageText);
        //отправляем сообщение
        telegramBot.execute(message);
        remindAddMessage(update);
    }

    //перечень всех команд
    private void remindAddMessage(Update update) {
        Long chatId = update.message().chat().id();

        if (notificationTaskService.saveMessage(update)) {
            String messageText = "Напоминание удачно сохранено";
            SendMessage message = new SendMessage(chatId, messageText);
            telegramBot.execute(message);
            return;
        }

        String messageText = "Введите команду из списка ниже:" + "\n" +
                "/start"+ "\n" +
                "/add";
        SendMessage message = new SendMessage(chatId, messageText);
        //отправляем сообщение
        telegramBot.execute(message);
    }

    @Scheduled(cron = "0 * * * * *") // метод вызывается в определенное время
    //каждая звездочка - секунды, минуты, час, месяц, год, день недели * * * * * *
    //0 - значит, что в секунды не вызываем, * - любое значение
    private void checkReminderAndSendToUser() {
        List<NotificationTask> listNotification = notificationTaskRepository.findAllByMessageDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        for (NotificationTask notificationTask : listNotification) {
            SendMessage message = new SendMessage(notificationTask.getUserId(), notificationTask.getMessage());
            telegramBot.execute(message);
            notificationTaskRepository.delete(notificationTask);
        }
    }

}
