package com.info.chatbot.factory.imp;



import com.info.chatbot.bot.imp.TelegramBotImp;
import com.info.chatbot.config.BotConfig;
import com.info.chatbot.factory.MenuBotBuilder;
import com.info.chatbot.factory.TelegramBotFactory;
import com.info.chatbot.service.AnalisesCourtCasesService;
import com.info.chatbot.service.SearchCourtCasesService;
import com.info.chatbot.service.imp.SubscribeServiceImpl;
import com.info.chatbot.service.imp.UserBotServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TelegramBotFactoryImp implements TelegramBotFactory {


    private final BotConfig config;
    private final SubscribeServiceImpl signedUpService;
    private final MenuBotBuilder menuBotBuilder;
    private final UserBotServiceImp userBotService;
    private final SearchCourtCasesService searchCourtService;
    private final AnalisesCourtCasesService analizCourtServiceService;

    private List<TelegramBotImp> bots = new ArrayList<>();

    public TelegramBotFactoryImp(BotConfig config, SubscribeServiceImpl signedUpService, MenuBotBuilder menuBotBuilder, UserBotServiceImp userBotService, SearchCourtCasesService searchCourtService, AnalisesCourtCasesService analizCourtServiceService) {
        this.config = config;
        this.signedUpService = signedUpService;
        this.menuBotBuilder = menuBotBuilder;
        this.userBotService = userBotService;
        this.searchCourtService = searchCourtService;
        this.analizCourtServiceService = analizCourtServiceService;
    }

    @Override
    public List<TelegramBotImp> createPullOfTelegram () {

        TelegramBotImp newBot = createNewBot(getConfig()
                .setBotName(config.getBotName())
                .setToken(config.getToken())
                .setSendActive(config.isSendActive()
                )
        );

        bots.add( createNewBot( getConfig()
                .setBotName(config.getBotName())
                .setToken(config.getToken())
                .setSendActive(config.isSendActive())));

        log.info("Run Telegram Bot: " + config.getBotName());
        return bots;
    }
    @Override
    public TelegramBotImp createNewBot(BotConfig config) {
        return new TelegramBotImp(
                config, signedUpService, menuBotBuilder, userBotService, searchCourtService, analizCourtServiceService );
    }
    @Override
    public BotConfig getConfig() {
        return new BotConfig();
    }
    @Override
    public List<TelegramBotImp> getBots() {
        return bots;
    }
}
