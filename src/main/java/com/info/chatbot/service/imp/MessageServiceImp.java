package com.info.chatbot.service.imp;


import com.info.chatbot.factory.TelegramBotFactory;

import com.info.chatbot.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;


@EnableScheduling
@Service
@Slf4j
@Scope("prototype")
public class MessageServiceImp implements MessageService {

	private final TelegramBotFactory BotFactory;

    public MessageServiceImp(TelegramBotFactory BotFactory) {
        this.BotFactory = BotFactory;
    }

    @Override
	public void startSendToTelegramBot(Long chatId, String textToSend, String caseNumber, String botName) {

		String errorMessage = BotFactory.getBots()
				.stream().filter(b->b.getBotUsername().equalsIgnoreCase((botName))).findFirst().get()
				.sendMessageToExternalChannel(chatId, textToSend, caseNumber);
		if (errorMessage == null) {
			log.info("Complete OK");
		} else {
			log.error(errorMessage);
		}
	}
}