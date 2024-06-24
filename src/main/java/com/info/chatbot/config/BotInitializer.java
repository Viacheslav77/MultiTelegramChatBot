package com.info.chatbot.config;


import com.info.chatbot.bot.imp.TelegramBotImp;
import com.info.chatbot.factory.imp.TelegramBotFactoryImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Component
public class BotInitializer {

    @Autowired
    TelegramBotImp bot;

    @Autowired
    TelegramBotFactoryImp fabric;

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        fabric.createPullOfTelegram();

        for (TelegramBotImp bot : fabric.getBots()) {
            try {
                telegramBotsApi.registerBot(bot);

            }
            catch (TelegramApiException e) {
                log.error("Error occurred: " + e.getMessage());

            }
        }
    }
}
