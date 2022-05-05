package ru.home.gymnastic_bot.botapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.home.gymnastic_bot.cache.UserDataCache;
import ru.home.gymnastic_bot.entity.UserProfileData;

import java.util.Map;
import java.util.Random;

@Slf4j
@Component
@EnableScheduling
public class SendReminderFromDB {

    private final gymnasticTelegramBot gymnasticTelegramBot;
    private final UserDataCache userDataCache;

    public SendReminderFromDB(@Lazy gymnasticTelegramBot gymnasticTelegramBot, UserDataCache userDataCache) {
        this.gymnasticTelegramBot = gymnasticTelegramBot;
        this.userDataCache = userDataCache;
    }

    @Scheduled(cron = "0 0 12 * * ?", zone = "GMT+3:00")
    private void startScheduleAt12(){
        sendTheReminderToAll();
    }


    @Scheduled(cron = "0 0 18 * * ?", zone = "GMT+3:00")
    private void startScheduleAt18(){
        sendTheReminderToAll();
    }

    private void sendTheReminderToAll(){
        log.info("try to send the reminder to all");
        Map<Long, UserProfileData> users = userDataCache.getUsers();
        if (!users.isEmpty()){
            for (Map.Entry<Long, UserProfileData> user: users.entrySet()) {

                String chatId = user.getValue().getChatId();

                Random random = new Random();
                int random50percent = random.nextInt(100);
                if (random50percent < 50) {
                    gymnasticTelegramBot.sendVideo(chatId, "videos/reminder1.mp4");
                } else {
                    gymnasticTelegramBot.sendVideo(chatId, "videos/reminder2.mp4");
                }

                gymnasticTelegramBot.sendReminder(chatId, "Пора сделать разминку");
            }
        }

        log.info("Reminders were sent");

    }
}
