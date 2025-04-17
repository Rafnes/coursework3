package pro.sky.telegrambot;

import com.pengrad.telegrambot.TelegramBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TelegramBotApplicationTests {
	@Autowired
	private ApplicationContext applicationContext;

	@Mock
	private TelegramBot telegramBot;

	@Mock
	private NotificationTaskRepository notificationTaskRepository;

	@InjectMocks
	private TelegramBotUpdatesListener listener;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		listener.init();
	}

	@Test
	void testUpdatesListener() {
		Object listener = applicationContext.getBean("telegramBotUpdatesListener");
		assertNotNull(listener);
	}

	@Test
	void testScheduler() {
		Object scheduler = applicationContext.getBean("scheduler");
		assertNotNull(scheduler);
	}
}
