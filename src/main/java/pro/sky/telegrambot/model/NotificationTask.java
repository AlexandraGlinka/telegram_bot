package pro.sky.telegrambot.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Data // lombok - по умолчанию настраивает конструктор, геттеры, сеттеры итд
public class NotificationTask {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private String messageText;
    private LocalDateTime messageDate;

}
