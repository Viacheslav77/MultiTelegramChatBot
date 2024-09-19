package com.info.chatbot;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.info.chatbot.controller.TelegramBotController;
import com.info.chatbot.entity.Subscribe;
import com.info.chatbot.service.MessageService;
import com.info.chatbot.service.SearchCourtCasesService;
import com.info.chatbot.service.SubscribeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

@SpringBootTest
    public class TelegramBotControllerTest {

            @InjectMocks
            private TelegramBotController telegramBotController;

            @Mock
            private SubscribeService subscribeService;

            @Mock
            private SearchCourtCasesService searchCourtService;

            @Mock
            private MessageService messageService;

            @BeforeEach
            void setUp() {
                ReflectionTestUtils.setField(telegramBotController, "schedulingCheck", true);
            }

            @Test
            void checkingNewCases_shouldSendMessagesForNewCases() {
                // Arrange
                List<Subscribe> subscribeList = new ArrayList<>();
                Subscribe subscribe1 = new Subscribe();
                subscribe1.setChatId(123L);
                subscribe1.setCaseNumber("12345");
                subscribeList.add(subscribe1);

                List<Subscribe> callToChats = new ArrayList<>();
                callToChats.add(subscribe1);

                when(subscribeService.getAll()).thenReturn(subscribeList);
                when(searchCourtService.searchingNewCases(subscribeList)).thenReturn(callToChats);

                telegramBotController.checkingNewCases();

                verify(messageService).startSendToTelegramBot(123L, "З'явилося нове рішення по судової справі №12345", "12345", "SupremeCourtInfoChatBot");
                verify(searchCourtService).searchingNewCases(subscribeList);
            }

        }
