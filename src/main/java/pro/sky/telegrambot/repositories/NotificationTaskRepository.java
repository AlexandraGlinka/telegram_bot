package pro.sky.telegrambot.repositories;

import com.sun.xml.bind.v2.model.core.ID;
import liquibase.pro.packaged.T;
import org.hibernate.sql.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.NotificationTask;
@Repository
public interface NotificationTaskRepository extends JpaRepository<NotificationTask, Long> {

}
