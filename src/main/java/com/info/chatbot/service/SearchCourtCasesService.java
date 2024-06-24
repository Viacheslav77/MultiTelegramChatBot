package com.info.chatbot.service;


import com.info.chatbot.dto.CourtCase;
import com.info.chatbot.entity.Subscribe;
import org.jvnet.hk2.annotations.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
public interface SearchCourtCasesService {

    List<String> getCOURT_CASE_CONSTANTS();

    String checkCourtCase(String messageText, Update update);

    String setValueToCourtCase(String expectedValue, String messageText);

    Subscribe saveSubscribeClient();

    void setNewCourtCase(long chatId);

    String getLastCaseNumber();

    CourtCase getCourtCase();

    long getActualChatId();

    String searchingByCaseNumber(String caseNumber);

    String getCourtCaseById(int id);

    String searchingFirstByCourtCase();

    List<Subscribe> searchingNewCases(List<Subscribe> subscribes);

    String searchingByCourtCase();

    int getCountShowDocResults();

    int getSizeLastSearching();

    String getLastSearching();
}
