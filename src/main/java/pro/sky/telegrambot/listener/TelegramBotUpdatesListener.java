package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Matcher;

import static pro.sky.telegrambot.util.BotUtil.*;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    NotificationTaskRepository notificationTaskRepository;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            if (update.message().text() == null) {
                logger.error("Null message");
                return;
            }
            SendMessage sendMessage = null;
            String text = update.message().text();
            Long chatId = update.message().chat().id();
            Matcher matcher = DATE_PATTERN.matcher(text);
            if (!isNotBlank(text)) {
                sendMessage = new SendMessage(chatId, "Некорректное сообщение. Попробуйте еще раз");
            } else if (text.equals(START)) {
                sendMessage = new SendMessage(chatId, HELLO_MESSAGE);
            } else if (matcher.matches()) {
                try {
                    LocalDateTime notificationDate = LocalDateTime.parse(matcher.group(1), DATE_FORMATTER);
                    if (notificationDate.isBefore(LocalDateTime.now())) {
                        logger.error("Некорректная дата");
                        sendMessage = new SendMessage(chatId, "Некорректная дата. Дата не может быть из прошлого времени");
                    } else {
                        notificationTaskRepository.save(new NotificationTask(
                                chatId,
                                matcher.group(3),
                                notificationDate
                        ));
                        logger.info("Уведомление добавлено: chatId:{}, text: {}, date: {}", chatId, text, notificationDate);
                        sendMessage = new SendMessage(chatId, "Уведомление успешно добавлено");
                    }
                } catch (DateTimeParseException e) {
                    logger.error("Некорретный формат даты {}", e.getMessage());
                    sendMessage = new SendMessage(chatId, "Некорректная дата");
                }
            } else {
                sendMessage = new SendMessage(chatId, "Некорректное сообщение. Сообщение должно быть в формате ДД:ММ:ГГГГ ЧЧ:мм. Попробуйте еще раз");
            }
            if (sendMessage != null) {
                telegramBot.execute(sendMessage);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public boolean isNotBlank(String text) {
        if (text.isBlank()) {
            logger.warn("Invalid message (blank)");
            return false;
        }
        return true;
    }
}
