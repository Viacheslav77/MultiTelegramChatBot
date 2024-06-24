package com.info.chatbot.bot;

public interface TelegramBot {


    void searchingByCaseNumber(String caseNumber, Long chatId);

    void searchingByCaseNumber(int id);

    String sendMessageToExternalChannel(Long chatId, String textToSend, String caseNamber);
}
