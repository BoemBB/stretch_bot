package ru.home.gymnastic_bot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.home.gymnastic_bot.cache.UserDataCache;
import ru.home.gymnastic_bot.entity.UserProfileData;
import ru.home.gymnastic_bot.gymnasticTelegramBot;

import java.util.*;

@Slf4j
@Service
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
        sendReminderToAll();
    }


    @Scheduled(cron = "0 0 18 * * ?", zone = "GMT+3:00")
    private void startScheduleAt18(){
        sendReminderToAll();
    }

    private void sendReminderToAll(){
        Map<Long, UserProfileData> users = userDataCache.getUsers();
        if (!users.isEmpty()){
            for (Map.Entry<Long, UserProfileData> user: users.entrySet()
                 ) {
                String chatId = user.getValue().getChatId();

                Random rnd = new Random();
                int b = rnd .nextInt(100);
                if (b < 50)
                    gymnasticTelegramBot.sendVideo(chatId,"videos/reminder1.mp4");
                else
                    gymnasticTelegramBot.sendVideo(chatId,"videos/reminder2.mp4");
                gymnasticTelegramBot.sendReminder(chatId, "Пора сделать разминку");
            }
        }
    }

    /*@SneakyThrows
    @Override
    public void afterPropertiesSet() {
        if (profileDataService.getAllProfiles() != null) {
            for (UserProfileData profileData : profileDataService.getAllProfiles()
            ) {
                if (profileData.getLocalDateTime() != null) {
                    log.info("try to send a reminder");
                    String chatId = profileData.getChatId();
                    LocalDateTime localDateTime = profileData.getLocalDateTime().minusHours(1);
                    if (profileData.getLocalDateTime() != null){
                        log.info("in if statement");
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {

                                Random rnd = new Random();
                                int b = rnd .nextInt(100);
                                if (b < 33)
                                    gymnasticTelegramBot.sendVideo(chatId,"videos/reminder1.mp4");
                                else if (b < 66)
                                    gymnasticTelegramBot.sendVideo(chatId,"videos/reminder2.mp4");
                                else
                                    gymnasticTelegramBot.sendVideo(chatId,"videos/reminder1.mp4");

                                gymnasticTelegramBot.sendReminder(chatId, "Пора сделать разминку");

                            }
                        }, java.sql.Timestamp.valueOf(localDateTime));
                    }
                }
            }
        }
    }

    @PreDestroy
    public void destroyMethod() {
        if (profileDataService.getAllProfiles() != null) {
            for (UserProfileData profileData : profileDataService.getAllProfiles()
            ) {
                if (profileData.getLocalDateTime() != null) {
                    log.info("upgrade date");
                    LocalDateTime localDateTime = profileData.getLocalDateTime().plusDays(1);
                    profileData.setLocalDateTime(localDateTime);
                }
            }
        }
    }*/
}
