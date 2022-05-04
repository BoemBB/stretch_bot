package ru.home.gymnastic_bot.controller;

import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.home.gymnastic_bot.gymnasticTelegramBot;


@RestController
public class WebHookController {

    private final gymnasticTelegramBot telegramBot;

    public WebHookController(gymnasticTelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return telegramBot.onWebhookUpdateReceived(update);
    }
}
