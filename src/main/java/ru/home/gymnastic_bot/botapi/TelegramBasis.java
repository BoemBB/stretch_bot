package ru.home.gymnastic_bot.botapi;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.home.gymnastic_bot.botapi.replyKeyboard.KeyboardUtils;
import ru.home.gymnastic_bot.entity.UserProfileData;
import ru.home.gymnastic_bot.gymnasticTelegramBot;
import ru.home.gymnastic_bot.cache.UserDataCache;
import ru.home.gymnastic_bot.service.UsersProfileDataService;

import java.util.concurrent.TimeUnit;

import java.util.*;

@Component
@Slf4j
public class TelegramBasis {
    private final UserDataCache userDataCache;
    private final gymnasticTelegramBot gymnasticTelegramBot;
    private final MessageSource messageSource;
    private final UsersProfileDataService profileDataService;
    private final KeyboardUtils keyboardUtils;

    public TelegramBasis(UserDataCache userDataCache, @Lazy gymnasticTelegramBot gymnasticTelegramBot,
                         MessageSource messageSource, UsersProfileDataService profileDataService, KeyboardUtils keyboardUtils) {
        this.userDataCache = userDataCache;
        this.gymnasticTelegramBot = gymnasticTelegramBot;
        this.messageSource = messageSource;
        this.profileDataService = profileDataService;
        this.keyboardUtils = keyboardUtils;
    }

    @SneakyThrows
    public SendMessage handleUpdate(Update update) {
        Message message = update.getMessage();
        String messageText = message.getText();
        String chatId = message.getChatId().toString();
        String userIdString = message.getFrom().getId().toString();
        String userName = message.getFrom().getUserName();
        long userIdLong = message.getFrom().getId();

        UserProfileData user = userDataCache.getOrCreateUserProfileData(userIdLong);

        if (user.getName() == null && user.getChatId() == null) {
            user.setName(userName);
            user.setChatId(userIdString);
            user.setUserId(userIdLong);
        }

        SendMessage replyMessage = null;

        if (message.hasText()) {

            ReplyKeyboardMarkup replyKeyboardMarkup;

            if (messageText.equals("/start")) {
                log.info("New message from User:{}, userId: {}, chatId: {},  with text: {}",
                        message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());

                replyKeyboardMarkup = keyboardUtils.getOneTimeKeyboard();
                List<KeyboardRow> keyboard = keyboardUtils.getKeyBoardRow("<15", "15-20", "20-25", "25-30","30-35", "35-40", ">40");
                replyKeyboardMarkup.setKeyboard(keyboard);
                replyMessage = new SendMessage(chatId, "Укажите ваш возраст");
                replyMessage.setReplyMarkup(replyKeyboardMarkup);

            } else if (messageText.equals("<15") || messageText.equals("15-20") || messageText.equals("20-25")
                    || messageText.equals("30-35") || messageText.equals("25-30") || messageText.equals(">40") || messageText.equals("35-40")) {

                log.info("New message from User:{}, userId: {}, chatId: {},  with text: {}",
                        message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());

                switch (messageText){
                    case "<15": user.setAge(10);
                        break;
                    case "15-20": user.setAge(17);
                        break;
                    case "20-25": user.setAge(22);
                        break;
                    case "25-30": user.setAge(27);
                        break;
                    case "30-35": user.setAge(32);
                        break;
                    case "35-40": user.setAge(43);
                        break;
                    case ">40": user.setAge(45);
                        break;
                }

                replyKeyboardMarkup = keyboardUtils.getReplyKeyboardMarkup2Button("М", "Ж");
                replyMessage = new SendMessage(chatId, "Укажите ваш пол");
                replyMessage.setReplyMarkup(replyKeyboardMarkup);

            } else if (messageText.equals("М") || messageText.equals("Ж")) {
                log.info("New message from User:{}, userId: {}, chatId: {},  with text: {}",
                        message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());

                switch (messageText){
                    case "М": user.setSex("М");
                        break;
                    case "Ж": user.setSex("Ж");
                        break;
                }

                replyKeyboardMarkup = keyboardUtils.getReplyKeyboardMarkup2Button("Активный", "Постоянно сижу");
                replyMessage = new SendMessage(chatId, "Какой у вас образ жизни?");
                replyMessage.setReplyMarkup(replyKeyboardMarkup);

            } else if (messageText.equals("Активный") || messageText.equals("Постоянно сижу")) {

                log.info("New message from User:{}, userId: {}, chatId: {},  with text: {}",
                        message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());

                switch (messageText){
                    case "Активный": user.setTypeOfLive("Активный");
                        break;
                    case "Постоянно сижу": user.setTypeOfLive("Постоянно сижу");
                        break;
                }

                replyKeyboardMarkup = keyboardUtils.getReplyKeyboardMarkup1Button("Да, конечно");
                replyMessage = new SendMessage(chatId, messageSource.getMessage("reply.firstText", null, Locale.forLanguageTag("$ru-RU")));
                replyMessage.setReplyMarkup(replyKeyboardMarkup);

            } else if (messageText.equals("Да, конечно")) {

                log.info("New message from User:{}, userId: {}, chatId: {},  with text: {}",
                        message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());

                replyKeyboardMarkup = keyboardUtils.getReplyKeyboardMarkup1Button("Прекрасно, да");
                replyMessage = new SendMessage(chatId, messageSource.getMessage("reply.secondText", null, Locale.forLanguageTag("$ru-RU")));
                replyMessage.setReplyMarkup(replyKeyboardMarkup);

            } else if (messageText.equals("Прекрасно, да")) {

                log.info("New message from User:{}, userId: {}, chatId: {},  with text: {}",
                        message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());

                replyKeyboardMarkup = keyboardUtils.getReplyKeyboardMarkup1Button("Звучит интересно");
                gymnasticTelegramBot.sendVideo(chatId, "Привет", "videos/theCat.mp4");
                replyMessage = new SendMessage(chatId,
                        messageSource.getMessage("reply.thirtyText", null, Locale.forLanguageTag("$ru-RU")));
                replyMessage.setReplyMarkup(replyKeyboardMarkup);

            } else if (messageText.equals("Звучит интересно")) {
                log.info("New message from User:{}, userId: {}, chatId: {},  with text: {}",
                        message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());

                replyKeyboardMarkup = keyboardUtils.getReplyKeyboardMarkup4Button("Начать курс",
                        "Расскажи о себе", "Расскажи подробнее о курсе", "Покажи пример упражнений");
                replyMessage = new SendMessage(chatId, messageSource.getMessage("reply.fourthText", null, Locale.forLanguageTag("$ru-RU")));
                replyMessage.setReplyMarkup(replyKeyboardMarkup);

            } else if (messageText.equals("Покажи пример упражнений")) {
                log.info("New message from User:{}, userId: {}, chatId: {},  with text: {}",
                        message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());
                log.info("Send video");

                gymnasticTelegramBot.sendVideo(chatId,  "videos/neck1.mp4");
                gymnasticTelegramBot.sendVideo(chatId,  "videos/back1.mp4");

                replyMessage = new SendMessage(chatId, "^-^"); // need to add message

            } else if (messageText.equals("Расскажи о себе")) {
                log.info("New message from User:{}, userId: {}, chatId: {},  with text: {}",
                        message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());

                replyMessage = new SendMessage(chatId, messageSource.getMessage("reply.tellAboutYourself", null, Locale.forLanguageTag("$ru-RU")));

            } else if (messageText.equals("Расскажи подробнее о курсе")) {
                log.info("New message from User:{}, userId: {}, chatId: {},  with text: {}",
                        message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());

                replyMessage = new SendMessage(chatId, messageSource.getMessage("reply.tellAboutACourse", null, Locale.forLanguageTag("$ru-RU")));

            } else if (messageText.equals("Начать курс")) {

                log.info("New message from User:{}, userId: {}, chatId: {},  with text: {}",
                        message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());

                replyKeyboardMarkup = keyboardUtils.getReplyKeyboardMarkup1Button("Да, давай");
                replyMessage = new SendMessage(chatId, messageSource.getMessage("reply.startCourse", null, Locale.forLanguageTag("$ru-RU")));
                replyMessage.setReplyMarkup(replyKeyboardMarkup);

            } else if (messageText.equals("Да, давай")) {
                log.info("New message from User:{}, userId: {}, chatId: {},  with text: {}",
                        message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());

                replyKeyboardMarkup = keyboardUtils.getReplyKeyboardMarkup4Button("Затекла шея",
                        "Затекла спина", "Болит поясница", "Хочу расслабиться");

                replyMessage = new SendMessage(chatId, "Отлично! Что разомнем?)");
                replyMessage.setReplyMarkup(replyKeyboardMarkup);

            } else if (messageText.equals("Хочу расслабиться")) {
                log.info("New message from User:{}, userId: {}, chatId: {},  with text: {}",
                        message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());
                log.info("Send video");

                gymnasticTelegramBot.sendVideo(chatId, "videos/back4.mp4");
                TimeUnit.SECONDS.sleep(5);
                gymnasticTelegramBot.sendVideo(chatId, "videos/neck2.mp4");
                TimeUnit.SECONDS.sleep(5);
                gymnasticTelegramBot.sendVideo(chatId, "videos/neck6.mp4");

                replyMessage = new SendMessage(chatId, "^-^");

            } else if (messageText.equals("Болит поясница")) {
                log.info("New message from User:{}, userId: {}, chatId: {},  with text: {}",
                        message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());
                log.info("Send video");
                replyMessage = new SendMessage(chatId, "В разработке, поэтому пока делаем это");
                gymnasticTelegramBot.sendVideo(chatId, "videos/memes.mp4");

            } else if (messageText.equals("Затекла шея")) {
                log.info("New message from User:{}, userId: {}, chatId: {},  with text: {}",
                        message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());
                log.info("Send video");

                gymnasticTelegramBot.sendExtraMessage(chatId,"На выполнение одного упражнения " +
                        "требуется примерно 45 секунд, выполняй в меру своих возможностей☺️\n" +
                                "Приступаем\uD83D\uDE0A");

                if (user.getChangeNeck() % 2 == 0) {
                    gymnasticTelegramBot.sendVideo(chatId, "videos/neck1.mp4");
                    TimeUnit.SECONDS.sleep(5);
                    gymnasticTelegramBot.sendVideo(chatId, "videos/neck2.mp4");
                    TimeUnit.SECONDS.sleep(5);
                    gymnasticTelegramBot.sendVideo(chatId, "videos/neck3.mp4");
                } else {
                    gymnasticTelegramBot.sendVideo(chatId, "videos/neck4.mp4");
                    TimeUnit.SECONDS.sleep(5);
                    gymnasticTelegramBot.sendVideo(chatId, "videos/neck5.mp4");
                    TimeUnit.SECONDS.sleep(5);
                    gymnasticTelegramBot.sendVideo(chatId, "videos/neck6.mp4");
                }
                user.changeRandomNeck();

                replyMessage = new SendMessage(chatId, "Как ощущения? Стало лучше?");
                replyKeyboardMarkup = keyboardUtils.getReplyKeyboardMarkup2Button("Да, " +
                                "все круто \uD83D\uDC4D",
                        "Я бы не отказался еще от упражнения");
                replyMessage.setReplyMarkup(replyKeyboardMarkup);

            } else if (messageText.equals("Затекла спина")) {
                log.info("New message from User:{}, userId: {}, chatId: {},  with text: {}",
                        message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());
                log.info("Send video");

                gymnasticTelegramBot.sendExtraMessage(chatId,"На выполнение одного упражнения " +
                        "требуется примерно 45 секунд, выполняй в меру своих возможностей☺️\n" +
                        "Приступаем\uD83D\uDE0A");

                if (user.getChangeBack() % 2 == 0) {
                    gymnasticTelegramBot.sendVideo(chatId, "videos/back1.mp4");
                    TimeUnit.SECONDS.sleep(5);
                    gymnasticTelegramBot.sendVideo(chatId, "videos/back2.mp4");
                    TimeUnit.SECONDS.sleep(5);
                    gymnasticTelegramBot.sendVideo(chatId, "videos/back3.mp4");
                } else {
                    gymnasticTelegramBot.sendVideo(chatId, "videos/back4.mp4");
                    TimeUnit.SECONDS.sleep(5);
                    gymnasticTelegramBot.sendVideo(chatId, "videos/back5.mp4");
                    TimeUnit.SECONDS.sleep(5);
                    gymnasticTelegramBot.sendVideo(chatId, "videos/back6.mp4");
                }
                user.changeRandomBack();

                replyMessage = new SendMessage(chatId, "Как ощущения? Стало лучше?");
                replyKeyboardMarkup = keyboardUtils.getReplyKeyboardMarkup2Button("Да, " +
                                "все круто \uD83D\uDC4D",
                        "Я бы не отказался еще от упражнения");
                replyMessage.setReplyMarkup(replyKeyboardMarkup);

            } else if (messageText.equals("Да, все круто \uD83D\uDC4D")) {
                replyMessage = new SendMessage(chatId, "Рад был помочь, пиши еще, я помогу");
                replyMessage.setReplyMarkup(keyboardUtils.getReplyKeyboardMarkup4Button("Затекла шея",
                        "Затекла спина", "Болит поясница", "Хочу расслабиться"));

            } else if (messageText.equals("Я бы не отказался еще от упражнения")) {
                replyMessage = new SendMessage(chatId, "Отлично, что разомнем?");
                replyMessage.setReplyMarkup(keyboardUtils.getReplyKeyboardMarkup4Button("Затекла шея",
                        "Затекла спина", "Болит поясница", "Хочу расслабиться"));
            }
            
            user.addMessage(messageText);
            profileDataService.saveUserProfileData(user);

        }

        if (replyMessage == null){
            replyMessage = new SendMessage(chatId, "Возникла какая-то ошибка((");
        }

        return replyMessage;
    }

}
