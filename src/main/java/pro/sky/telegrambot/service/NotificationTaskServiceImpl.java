package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.model.Update;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repositories.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class NotificationTaskServiceImpl implements NotificationTaskService {
    @Autowired
    private NotificationTaskRepository notificationTaskRepository;

    @Override
    public boolean saveMessage(Update update) {
        Long userId = update.message().chat().id();
        String message = update.message().text();
        //Паттерн распознавания даты и текста
        Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
        Matcher matcher = pattern.matcher(message);
        if (matcher.matches()) {
            String date = matcher.group(1);
            String textMessage = matcher.group(3);
            LocalDateTime dateTime;
            try {
                dateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
                NotificationTask notificationTask = new NotificationTask();
                notificationTask.setUserId(userId);
                notificationTask.setMessageDate(dateTime);
                notificationTask.setMessage(textMessage);

                notificationTaskRepository.save(notificationTask);

                log.info("Message is added: {}", notificationTask);

                return true;

            } catch (Exception e) {
                log.info("Datatype is incorrect");
                return false;
            }
        }
        return false;
    }

}
