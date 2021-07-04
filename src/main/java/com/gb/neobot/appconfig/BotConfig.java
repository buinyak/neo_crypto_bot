package com.gb.neobot.appconfig;

import com.gb.neobot.NeoTelegramBot;
import com.gb.neobot.botapi.TelegramFacade;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {
    private String webHookPath;
    private String botUserName;
    private String botToken;

    @Bean
    public NeoTelegramBot neoTelegramBot(TelegramFacade telegramFacade) {
        DefaultBotOptions options = ApiContext
                .getInstance(DefaultBotOptions.class);
        NeoTelegramBot neoTelegramBot = new NeoTelegramBot(options,telegramFacade);
        neoTelegramBot.setBotToken(botToken);
        neoTelegramBot.setBotUserName(botUserName);
        neoTelegramBot.setWebHookPath(webHookPath);
        return neoTelegramBot;
    }

}
