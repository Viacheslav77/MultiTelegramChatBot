package com.info.chatbot.service.imp;


import com.info.chatbot.factory.TelegramBotFactory;

import com.info.chatbot.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;


@EnableScheduling
@Service
@Slf4j
public class MessageServiceImp implements MessageService {

	private final TelegramBotFactory BotFactory;

    public MessageServiceImp(TelegramBotFactory BotFactory) {
        this.BotFactory = BotFactory;
    }

    @Override
	public void startSendToTelegramBot(Long chatId, String textToSend, String caseNamber) {
		int botNumber = 0;
		String errorMessage = BotFactory.getBots().get(botNumber)
				.sendMessageToExternalChannel(chatId, textToSend, caseNamber);
		if (errorMessage == null) {
			log.info("Complete OK");
		} else {
			log.error(errorMessage);
		}
	}



}