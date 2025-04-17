package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class NotificationTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id", nullable = false)
    private Long chatId;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "notification_date", nullable = false)
    private LocalDateTime notificationDate;

    public NotificationTask() {
    }

    public NotificationTask(Long chatId, String text, LocalDateTime notificationDate) {
        this.chatId = chatId;
        this.text = text;
        this.notificationDate = notificationDate;
        creationDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(LocalDateTime notificationDate) {
        this.notificationDate = notificationDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        NotificationTask task = (NotificationTask) obj;
        return Objects.equals(id, task.id) && Objects.equals(chatId, task.chatId) && Objects.equals(text, task.text) && Objects.equals(creationDate, task.creationDate) && Objects.equals(notificationDate, task.notificationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, text, creationDate, notificationDate);
    }

    @Override
    public String toString() {
        return "NotificationTask{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", text='" + text + '\'' +
                ", creationDate=" + creationDate +
                ", notificationDate=" + notificationDate +
                '}';
    }
}
