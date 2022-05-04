package ru.home.gymnastic_bot;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.home.gymnastic_bot.botapi.TelegramBasis;

import java.io.File;
import java.io.InputStream;

public class gymnasticTelegramBot extends TelegramWebhookBot {
    private String webHookPath;
    private String botUserName;
    private String botToken;

    private final TelegramBasis telegramBasis;


    public gymnasticTelegramBot(DefaultBotOptions botOptions, TelegramBasis telegramBasis) {
        super(botOptions);
        this.telegramBasis = telegramBasis;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotPath() {
        return webHookPath;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return telegramBasis.handleUpdate(update);
    }

    public void setWebHookPath(String webHookPath) {
        this.webHookPath = webHookPath;
    }

    public void setBotUserName(String botUserName) {
        this.botUserName = botUserName;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    @SneakyThrows
    public void sendExtraMessage(String chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        execute(message);
    }

    @SneakyThrows
    public void sendVideo(String chatId, String videoCaption, String videoPath) {
        SendVideo sendVideo = setVideo(chatId, videoPath);
        sendVideo.setCaption(videoCaption);
        execute(sendVideo);
        /*File video = ResourceUtils.getFile("classpath:" + videoPath);
        SendVideo sendVideo = new SendVideo();
        sendVideo.setVideo(new InputFile(video));
        sendVideo.setChatId(chatId);
        sendVideo.setCaption(videoCaption);
        execute(sendVideo);*/
    }

    @SneakyThrows
    public void sendVideo(String chatId, String videoPath) {
        SendVideo sendVideo = setVideo(chatId, videoPath);
        execute(sendVideo);
        /*File video = ResourceUtils.getFile("classpath:" + videoPath);
        SendVideo sendVideo = new SendVideo();
        sendVideo.setVideo(new InputFile(video));
        sendVideo.setChatId(chatId);
        execute(sendVideo);*/
    }

    @SneakyThrows
    public SendVideo setVideo(String chatId, String videoPath){
        File file = File.createTempFile("temp", "file");
        InputStream inputStream = new ClassPathResource("classpath:" + videoPath).getInputStream();
        FileUtils.copyInputStreamToFile(inputStream, file);
        SendVideo sendVideo = new SendVideo();
        sendVideo.setVideo(new InputFile(file));
        sendVideo.setChatId(chatId);
        return sendVideo;
    }

    @SneakyThrows
    public void sendReminder(String chatId, String text){
        execute(new SendMessage(chatId, text));
    }

}
