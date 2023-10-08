package pro.sky.telegrambot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repositories.NotificationTaskRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NotificationTaskServiceImpl implements NotificationTaskService {
    private final NotificationTaskRepository notificationTaskRepository;

    public NotificationTaskServiceImpl(NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskRepository = notificationTaskRepository;
    }

    Logger logger = LoggerFactory.getLogger(NotificationTaskServiceImpl.class);

    @Override
    public void saveMessage(NotificationTask notificationTask) {
        logger.debug("Message is added: {}", notificationTask);
        //Паттерн распознавания даты и текста
        Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
        Matcher matcher = pattern.matcher((CharSequence) notificationTask);
        if (matcher.matches()) {
            String date = matcher.group(1);
            String item = matcher.group(3);
        }
        notificationTaskRepository.save(notificationTask);
    }

    @Override
    public void saveTextMessage(NotificationTask notificationTask) {

    }
}
