package com.info.chatbot.service;


import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;


@EnableScheduling
@Service
public interface MessageService {

	void startSendToTelegramBot(Long chatId, String textToSend, String id);
}