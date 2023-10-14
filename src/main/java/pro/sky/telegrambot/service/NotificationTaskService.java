package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.model.Update;
import pro.sky.telegrambot.model.NotificationTask;

import java.util.List;

public interface NotificationTaskService {
    boolean saveMessage(Update update);

}
