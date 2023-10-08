package pro.sky.telegrambot.service;

import pro.sky.telegrambot.model.NotificationTask;

import java.util.List;

public interface NotificationTaskService {
    void saveMessage(NotificationTask notificationTask);

    void saveTextMessage(NotificationTask notificationTask);
}
