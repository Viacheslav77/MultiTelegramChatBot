package com.info.chatbot.controller;



import com.info.chatbot.entity.Subscribe;
import com.info.chatbot.service.MessageService;
import com.info.chatbot.service.SearchCourtCasesService;
import com.info.chatbot.service.SubscribeService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.List;


@EnableScheduling
@Controller
@Slf4j
@Data
public class TelegramBotController {

	@Value("${bot.scheduling.check}")
	private boolean schedulingCheck;

	private final SubscribeService subscribeService;
	private final SearchCourtCasesService searchCourtService;
	private final MessageService messageService;

	@Scheduled(fixedRateString  = "${timer.fixedrate}")
    public void checkingNewCases() {

		if (schedulingCheck) {
			log.info("Started checking New Cases for Telegram bot ... " );

			List<Subscribe> subscribeList = subscribeService.getAll();
			if (!subscribeList.isEmpty()) {

				List<Subscribe> callToChats = searchCourtService.searchingNewCases(subscribeList);

				if (!callToChats.isEmpty()) {
					for (Subscribe item : callToChats) {

						String mess = "З'явилося нове рішення по судової справі №" + item.getCaseNumber();
						messageService.startSendToTelegramBot(
								item.getChatId(), mess, item.getCaseNumber(), item.getBotName());
						log.info("Bot:" + item.getBotName() + " Sent message " + mess + " to " + item.getChatId());
					}
				}
			}
			log.info("Ended checking New Cases for Telegram bot ... " );
		}
	}
}