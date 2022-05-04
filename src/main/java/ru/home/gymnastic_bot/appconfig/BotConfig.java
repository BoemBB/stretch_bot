package ru.home.gymnastic_bot.appconfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import ru.home.gymnastic_bot.botapi.TelegramMain;
import ru.home.gymnastic_bot.botapi.gymnasticTelegramBot;


@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {
    private String webHookPath;
    private String botUserName;
    private String botToken;

    //uncomment if using local version
    /*private DefaultBotOptions.ProxyType proxyType;
    private String proxyHost;
    private int proxyPort;*/

    @Bean
    public gymnasticTelegramBot myGymnasticTelegramBot(TelegramMain telegramMain) {
        DefaultBotOptions options = new DefaultBotOptions();

        //uncomment if using local version
        /*options.setProxyHost(proxyHost);
        options.setProxyPort(proxyPort);
        options.setProxyType(proxyType);*/

        gymnasticTelegramBot myGymnasticTelegramBot = new gymnasticTelegramBot(options, telegramMain);
        myGymnasticTelegramBot.setBotUserName(botUserName);
        myGymnasticTelegramBot.setBotToken(botToken);
        myGymnasticTelegramBot.setWebHookPath(webHookPath);

        return myGymnasticTelegramBot;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages_ru_RU");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
