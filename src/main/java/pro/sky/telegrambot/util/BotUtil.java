package pro.sky.telegrambot.util;

import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class BotUtil {
    public static final String START = "/start";
    public static final String HELLO_MESSAGE = "Привет-привет!";
    public static final Pattern DATE_PATTERN = Pattern.compile("(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})(\\s+)(.+)");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
}
