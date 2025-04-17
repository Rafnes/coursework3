package pro.sky.telegrambot.scheduler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class Scheduler {
    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    NotificationTaskRepository notificationTaskRepository;

    @Scheduled(cron = "0 0/1 * * * *")
    public void sendNotifications() {
        List<NotificationTask> tasks = notificationTaskRepository.findByNotificationDateBetween(LocalDateTime.now().minusMinutes(1), LocalDateTime.now());
        tasks.forEach(task -> {
            telegramBot.execute(new SendMessage(task.getChatId(), task.getText()));
        });
    }
}
