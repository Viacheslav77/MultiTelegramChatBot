package com.info.chatbot.factory;



import com.info.chatbot.bot.imp.TelegramBotImp;
import com.info.chatbot.config.BotConfig;

import java.util.List;

public interface TelegramBotFactory {

    List<TelegramBotImp> createPullOfTelegram();

    List<TelegramBotImp> getBots();

    TelegramBotImp createNewBot(BotConfig config);

    BotConfig getConfig();
}
