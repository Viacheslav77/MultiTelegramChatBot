package com.info.chatbot.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@Data
@Accessors(chain = true)
public class BotConfig {

    @Value("${bot.name.one}")
    String botName;

    @Value("${bot.token}")
    String token;

    @Value("${bot.send.active}")
    boolean sendActive;


}
