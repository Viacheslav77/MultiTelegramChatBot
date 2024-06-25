package com.info.chatbot.bot.imp;

import com.info.chatbot.bot.TelegramBot;
import com.info.chatbot.config.BotConfig;
import com.info.chatbot.constants.CourtForm;
import com.info.chatbot.dto.CourtCase;
import com.info.chatbot.entity.Subscribe;
import com.info.chatbot.factory.MenuBotBuilder;
import com.info.chatbot.repository.SubscribeRepository;
import com.info.chatbot.service.AnalisesCourtCasesService;
import com.info.chatbot.service.imp.SubscribeServiceImpl;
import com.info.chatbot.service.imp.UserBotServiceImp;
import com.info.chatbot.service.SearchCourtCasesService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.List;

import static com.info.chatbot.constants.MessageText.*;
import static com.info.chatbot.factory.MessageBotBuilder.*;


@EqualsAndHashCode(callSuper = true)
@Slf4j
@Controller
@Data
public class TelegramBotImp extends TelegramLongPollingBot implements TelegramBot {

    private SubscribeRepository subscribeRepository;

    private final SubscribeServiceImpl subscribeService;

    private final BotConfig config;
    private final MenuBotBuilder menuBotBuilder;
    private final UserBotServiceImp userBotService;
    private final SearchCourtCasesService searchCourtService;
    private final AnalisesCourtCasesService analysisCourtService;

    private String expectedValue;

    private final boolean active;

    public TelegramBotImp( BotConfig config,
                           SubscribeServiceImpl subscribeService,
                           MenuBotBuilder menuBotBuilder,
                           UserBotServiceImp userBotService,
                           SearchCourtCasesService searchCourtService,
                           AnalisesCourtCasesService analysisCourtService) {

        this.subscribeService = subscribeService;
        this.config = config;
        this.active = config.isSendActive();
        this.menuBotBuilder = menuBotBuilder;
        this.userBotService = userBotService;
        this.searchCourtService = searchCourtService;
        this.analysisCourtService = analysisCourtService;

        List<BotCommand> listOfCommands = menuBotBuilder.getListOfCommands();

        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMyChatMember()){
            Chat chat = update.getMyChatMember().getChat();
            Long idFrom = update.getMyChatMember().getFrom().getId();
            String firstName = update.getMyChatMember().getFrom().getFirstName();

            executeMessage(menuBotBuilder.getMessage(
                    idFrom,  getChatIdReceived( chat, firstName, config.getBotName())));
        }
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            log.info("Received from {} ", chatId);

            if (messageText.equals("/start") || messageText.equals("В головне меню") ||
                    messageText.equals("start") || messageText.equals("Update message text") ||
                    messageText.equals("update_msg_text")) {

                userBotService.registerUser(update.getMessage());
                executeMessage(menuBotBuilder.getMainMenu(chatId, TEXT_MAIN_MENU));
                //Пошук судових справ
            } else if ( messageText.equals(SEARCH_COURT_CASES) || messageText.equals("/searchCourtCases") ) {
                searchCourtService.setNewCourtCase(chatId);
                executeMessage(menuBotBuilder.getSearchCourtCases(chatId, TEXT_SEARCH_COURT_CASES ));
                expectedValue = "keywordsInJudgment";

            } else if ( messageText.equals(COURT_PRACTICE) || messageText.equals(RETURN_TO_COURT_PRACTICE)) {
                executeMessage(menuBotBuilder.getCourtPractice(chatId, TEXT_COURT_PRACTICE));

            } else if ( messageText.equals(GRAND_CHAMBER_SUPREME_COURT_DIGESTS)) {
                executeMessage(menuBotBuilder.getGrandChamber(chatId, TEXT_COURT_PRACTICE ));

            } else if ( messageText.equals(PERIODIC_REVIEWS_GRAND_CHAMBER)) {
                sendTypingStatus(chatId);
                executeMessage(menuBotBuilder.getGrandChamber(chatId, analysisCourtService.parsingItemAnalises(0) ));

            } else if ( messageText.equals(THEMATIC_REVIEWS_GRAND_CHAMBER)) {
                sendTypingStatus(chatId);
                executeMessage(menuBotBuilder.getGrandChamber(chatId, analysisCourtService.parsingItemAnalises(1) ));

            } else if (messageText.equals(REVIEWS_CASSATION_COURTS) || messageText.equals(RETURN_TO_PERIODIC_REVIEWS_GRAND_CHAMBER)) {
                executeMessage(menuBotBuilder.getReviewsCassation(chatId, TEXT_COURT_PRACTICE));

            } else if (messageText.equals(PERIODIC_REVIEWS_CASSATION_COURTS)) {
                executeMessage(menuBotBuilder.getPeriodicCassation(chatId, TEXT_COURT_PRACTICE));

            } else if (messageText.equals(CASSATION_ADMINISTRATIVE_COURT)) {
                sendTypingStatus(chatId);
                executeMessage(menuBotBuilder.getPeriodicCassation(chatId,
                        analysisCourtService.parsingItemAnalises(2)));
            } else if (messageText.equals(CASSATION_CIVIL_COURT)) {
                sendTypingStatus(chatId);
                executeMessage(menuBotBuilder.getPeriodicCassation(chatId,
                        analysisCourtService.parsingItemAnalises(5)));

            } else if (messageText.equals(CASSATION_COMMERCIAL_COURT)) {
                sendTypingStatus(chatId);
                executeMessage(menuBotBuilder.getPeriodicCassation(chatId,
                        analysisCourtService.parsingItemAnalises(3)));

            } else if (messageText.equals(CASSATION_CRIMINAL_COURT)) {
                sendTypingStatus(chatId);
                executeMessage(menuBotBuilder.getPeriodicCassation(chatId,
                        analysisCourtService.parsingItemAnalises(4)));

            } else if (messageText.equals(THEMATIC_REVIEWS_CASSATION_COURTS) || messageText.equals(RETURN_TP_THEMATIC_REVIEWS_CASSATION_COURTS)) {
                executeMessage(menuBotBuilder.getThematicCassation(chatId, TEXT_SEARCH_COURT_CASES));

            } else if (messageText.equals(THEMATIC_ADMINISTRATIVE_COURT)) {
                sendTypingStatus(chatId);
                executeMessage(menuBotBuilder.getThematicCassation(chatId,
                        analysisCourtService.parsingItemAnalises(6)));

            } else if (messageText.equals(THEMATIC_CIVIL_COURT)) {
                sendTypingStatus(chatId);
                executeMessage(menuBotBuilder.getThematicCassation(chatId,
                        analysisCourtService.parsingItemAnalises(9)));

            } else if (messageText.equals(THEMATIC_COMMERCIAL_COURT)) {
                sendTypingStatus(chatId);
                executeMessage(menuBotBuilder.getThematicCassation(chatId,
                        analysisCourtService.parsingItemAnalises( 7 )));

            } else if (messageText.equals(THEMATIC_CRIMINAL_COURT)) {
                sendTypingStatus(chatId);
                executeMessage(menuBotBuilder.getThematicCassation(chatId,
                        analysisCourtService.parsingItemAnalises(8)));

            } else if (messageText.equals(ECHR_DECISIONS_REVIEWS) || messageText.equals(RETURN_TO_PERIODIC_REVIEWS_ECHR)) {
                executeMessage(menuBotBuilder.getECHRDecisionsReviews(chatId, TEXT_COURT_PRACTICE));

            } else if (messageText.equals(PERIODIC_REVIEWS_ECHR) ) {
                sendTypingStatus(chatId);
                executeMessage(menuBotBuilder.getECHRDecisionsReviews(chatId, analysisCourtService.parsingItemAnalises(10)));

            } else if (messageText.equals(THEMATIC_REVIEWS_ECHR)) {
                sendTypingStatus(chatId);
                executeMessage(menuBotBuilder.getECHRDecisionsReviews(chatId, analysisCourtService.parsingItemAnalises(11)));

            } else if (messageText.equals(EU_COURT_PRACTICE_REVIEWS)) {
                sendTypingStatus(chatId);
                executeMessage(menuBotBuilder.getCourtPractice(chatId, analysisCourtService.parsingItemAnalises(12)));

            } else if (messageText.contains("/search_court_")) {
                sendTypingStatus(chatId);
                searchingByCaseNumber(Integer.parseInt(messageText.split("_")[2]));

            } else if (searchCourtService.getCOURT_CASE_CONSTANTS().contains(messageText)) {
                if (searchCourtService.getCourtCase() == null) {
                    searchCourtService.setNewCourtCase(chatId);
                }
                expectedValue = searchCourtService.checkCourtCase(messageText, update);
                executeMessage(menuBotBuilder.getSearchCourtCases(chatId, TEXT_TYPE));

            } else if ((messageText.equals(COURT_FORM_LABEL))) {
                executeMessage(menuBotBuilder.getCourtForm(chatId, TEXT_COURT_FORM_LABEL));
                expectedValue = "courtForm";

                //Форма судочінства
            } else if (Arrays.stream(CourtForm.values()).map(CourtForm::getLabel).toList().contains(messageText)) {
                executeMessage(menuBotBuilder.getGoalSearching(chatId, searchCourtService.setValueToCourtCase(expectedValue, messageText)));
                executeMessage(menuBotBuilder.getSearchCourtCases(chatId, TEXT_NEXT_SEARCH_COURT_CASES ));
                expectedValue = "keywordsInJudgment";

            } else if (messageText.equals(COURT_CASES) || messageText.equals("/court")) {
                executeMessage(menuBotBuilder.getCourtCasesAnJudicialPracticeMenu(chatId, TEXT_MAIN_MENU));

            } else if (messageText.equals(HELP) || messageText.equals("/help")) {
                executeMessage(menuBotBuilder.getHelpMenu(chatId, TEXT_COURT_PRACTICE));

            } else if (messageText.equals(ELECTRONIC_COURT)) {
                sendTypingStatus(chatId);
                executeMessage(menuBotBuilder.getHelpMenu(chatId, analysisCourtService.buildTextElectronicCourt()));

            } else if (messageText.equals(RECEPTION_OF_CITIZENS)) {
                sendTypingStatus(chatId);
                executeMessage(menuBotBuilder.getHelpMenu(chatId, analysisCourtService.buildTextReseptionOfCitizen()));

            } else if (messageText.equals(SAMPLE_DOCUMENTS)) {
                sendTypingStatus(chatId);
                executeMessage(menuBotBuilder.getHelpMenu(chatId, analysisCourtService.buildTextSampleDocuments()));

            } else if (messageText.equals(HOW_TO_BEHAVE_IN_COURT)) {
                sendTypingStatus(chatId);
                executeMessage(menuBotBuilder.getHelpMenu(chatId, analysisCourtService.buildTextBehaveToCort()));

            } else if (messageText.equals(ACTION_SUBSCRIBE)) {
                executeMessage(menuBotBuilder.getSearchCourtCases(chatId, TEXT_SEARCH_COURT_CASES));

            } else if (expectedValue != null && CourtCase.fieldsName.contains(expectedValue)) {

                executeMessage(menuBotBuilder.getGoalSearching(chatId, searchCourtService.setValueToCourtCase(expectedValue, messageText)));
                executeMessage(menuBotBuilder.getSearchCourtCases(chatId, TEXT_NEXT_SEARCH_COURT_CASES));
                expectedValue = "keywordsInJudgment";

            } else {
                executeMessage(menuBotBuilder.getMainMenu(chatId, ERROR_TEXT_INPUTS_SEARCH_COURT_CASES));
            }


        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();

            long chatId = update.getCallbackQuery().getMessage().getChatId();

            if(callbackData.equals(SEARCHING_CASES)){
                sendTypingStatus(chatId);
                String searchingResult = searchCourtService.searchingByCourtCase();

                if (searchCourtService.getSizeLastSearching() > searchCourtService.getCountShowDocResults()) {
                    executeMessage(menuBotBuilder.getNextResults(chatId,searchingResult));
                } else {
                    executeMessage(menuBotBuilder.getSearchCourtCases(chatId, searchingResult));
                }
            }
            if (callbackData.equals(NEXT_CASES)) {
                sendTypingStatus(chatId);
                if (searchCourtService.getSizeLastSearching() > searchCourtService.getCountShowDocResults()) {
                    executeMessage(menuBotBuilder.getNextResults(chatId, searchCourtService.getLastSearching()));
                } else {
                    executeMessage(menuBotBuilder.getSearchCourtCases(chatId, searchCourtService.getLastSearching()));
                }
            }
            if (callbackData.equals(NEW_SEARCHING_CASES)) {
                sendTypingStatus(chatId);
                searchCourtService.setNewCourtCase(chatId);
                executeMessage(menuBotBuilder.getSearchCourtCases(chatId, TEXT_SEARCH_COURT_CASES));
            }
            if (callbackData.equals(ACTION_SUBSCRIBE)) {
                Subscribe newSubscribe = searchCourtService.saveSubscribeClient();
                newSubscribe.setChatId(chatId);
                subscribeService.create(newSubscribe);
                executeMessage(menuBotBuilder.getSearchCourtCases(chatId, SIGNED_UP_TEXT + searchCourtService.getLastCaseNumber()));
            }
            if (callbackData.contains(ACTION_UNSUBSCRIBE)) {
                String caseNumber = callbackData.split("_")[1];
                Subscribe newSubscribe = searchCourtService.saveSubscribeClient();
                newSubscribe.setChatId(chatId);
                subscribeService.deleteByCaseNumber(caseNumber);
                executeMessage(menuBotBuilder.getSearchCourtCases(chatId, SIGNED_UP_TEXT + searchCourtService.getLastCaseNumber()));
            }
        }
    }

    @Override
    public void searchingByCaseNumber(String caseNumber, Long chatId) {

    }

    @Override
    public void searchingByCaseNumber(int id) {

        String caseNumber = searchCourtService.getCourtCaseById(id);

        String searchingResult = searchCourtService.searchingByCaseNumber(caseNumber);
        long actualChatId = searchCourtService.getActualChatId();

        if (searchCourtService.getSizeLastSearching() > searchCourtService.getCountShowDocResults()) {
            executeMessage(menuBotBuilder.getSubscribeButtonAndNext(actualChatId,searchingResult));
        } else {
            executeMessage(menuBotBuilder.getSubscribeButton(actualChatId, searchingResult));
        }
    }

    public void register(long chatId) {
        executeMessage(menuBotBuilder.getRegister(chatId));
    }

    @Override
    public String sendMessageToExternalChannel(Long chatId, String textToSend, String caseNamber) {

        log.info("Send message to chatId:" + chatId);
        String errorMessage = null;
        if (active) {
            try {
                execute(menuBotBuilder.getUnsubscribeMessage(chatId, textToSend, caseNamber));
            } catch (TelegramApiException e) {
                log.error(ERROR_TEXT + e.getMessage());
                errorMessage = e.getMessage();
            }
        }
        return errorMessage;
    }

    private void sendTypingStatus(long chatId) {
        SendChatAction chatAction = new SendChatAction();
        chatAction.setChatId(String.valueOf(chatId));
        chatAction.setAction(ActionType.TYPING);
        try {
            execute(chatAction);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void executeMessage(EditMessageText message){
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(ERROR_TEXT + e.getMessage());
        }
    }
    private void executeMessage(SendMessage message){

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(ERROR_TEXT + e.getMessage());
        }
    }
}
