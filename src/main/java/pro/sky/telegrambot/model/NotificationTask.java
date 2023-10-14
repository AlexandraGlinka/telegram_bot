package pro.sky.telegrambot.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Data // lombok - по умолчанию настраивает конструктор, геттеры, сеттеры итд
public class NotificationTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //первичный ключ
    private Long userId; //id пользователя/чата
    private String message;
    private LocalDateTime messageDate;

}
