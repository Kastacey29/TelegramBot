package com.example.telegrambot.timer;

import com.example.telegrambot.repository.NotificationTaskRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Component
public class NotificationTaskTimer {
    private final TelegramBot telegramBot;

    private final NotificationTaskRepository notificationTaskRepository;

    public NotificationTaskTimer(TelegramBot telegramBot, NotificationTaskRepository notificationTaskRepository) {
        this.telegramBot = telegramBot;
        this.notificationTaskRepository = notificationTaskRepository;
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void task() {
        notificationTaskRepository.findNotificationTasksByNotificationDateTime(
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        ).forEach(notificationTask -> {
            telegramBot.execute(
                    new SendMessage(notificationTask.getUserId(), "Вы просили напомнить о задаче: " + notificationTask.getMessage()));
            notificationTaskRepository.delete(notificationTask);
        });
    }
}

