package com.info.chatbot;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import com.info.chatbot.bot.imp.TelegramBotImp;
import com.info.chatbot.factory.imp.TelegramBotFactoryImp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Slf4j
public class MyTelegramBotIntegrationTest {
    @Mock
    private TelegramBotFactoryImp myFabric;
    @Mock
    private Update update;

    @Autowired
    TelegramBotFactoryImp fabric;

    TelegramBotImp telegramBot;

    @BeforeEach
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
        telegramBot = fabric.getBots().get(0);
    }

    @Test
    public void testBotResponse() throws TelegramApiException {
        telegramBot.onUpdateReceived(update);
        verify(update).hasMyChatMember();
    }

    @Test
    void onUpdateReceived_shouldHandleMyChatMember() {
        telegramBot.onUpdateReceived(update);
        verify(update).hasMyChatMember();
    }
    @Test
    void onUpdateReceived_shouldHandleMessage() {
        telegramBot.onUpdateReceived(update);
        verify(update).hasMessage();
    }

}
