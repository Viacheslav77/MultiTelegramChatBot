package com.info.chatbot.factory;

import com.info.chatbot.constants.CourtForm;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static com.info.chatbot.constants.MessageText.*;
import static com.info.chatbot.factory.MessageBotBuilder.NO_BUTTON;
import static com.info.chatbot.factory.MessageBotBuilder.YES_BUTTON;


@Slf4j
@Service
@Accessors(chain = true)
public class MenuBotBuilder {

    public List<BotCommand> getListOfCommands() {
        return List.of(
                new BotCommand("/start", MAIN_MENU),
                new BotCommand("/court", COURT_CASES),
                new BotCommand("/help", HELP)
        );
    }
    public SendMessage getGoalSearching(long chatId, String textToSend) {
        return addSearchButton(getSearchCourtCases(chatId, textToSend));
    }
    public SendMessage getSubscribeButtonAndNext(long chatId, String textToSend) {
        return addSubscribeButton(getSearchCourtCases(chatId, textToSend));
    }
    public SendMessage getSubscribeButton(long chatId, String textToSend) {
        return addSubscribeButton(getNextResults(chatId, textToSend));
    }
    public SendMessage getNextResults(long chatId, String textToSend) {
        return addNextButton(getSearchCourtCases(chatId, textToSend));
    }
    public SendMessage addSubscribeButton(SendMessage sendMessage) {
        return addActionButton(sendMessage, ACTION_SUBSCRIBE, ACTION_SUBSCRIBE);
    }
    public SendMessage addUnsubscribeButton(SendMessage sendMessage, String id) {
        return addActionButton(sendMessage, ACTION_UNSUBSCRIBE, ACTION_UNSUBSCRIBE + "_" + id);
    }
    public SendMessage getMainMenu(long chatId, String textToSend) {

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        KeyboardRow row4 = new KeyboardRow();
        KeyboardRow row5 = new KeyboardRow();

        row1.add(COURT_CASES);
        row2.add(SEARCH_COURT_CASES);
        row2.add(COURT_PRACTICE);
        row3.add(HELP);
        row4.add(ELECTRONIC_COURT);
        row4.add(RECEPTION_OF_CITIZENS);
        row5.add(SAMPLE_DOCUMENTS);
        row5.add(HOW_TO_BEHAVE_IN_COURT);

        List<KeyboardRow> keyboardRows = List.of(row1, row2, row3, row4, row5);
        
        return buildMessage(chatId, textToSend, keyboardRows);
    }

    public SendMessage getDiffMainMenu(long chatId, String textToSend) {

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        KeyboardRow row2 = new KeyboardRow();

        KeyboardRow row4 = new KeyboardRow();
        KeyboardRow row5 = new KeyboardRow();


        row2.add(SEARCH_COURT_CASES);
        row2.add(COURT_PRACTICE);
        row4.add(ELECTRONIC_COURT);
        row4.add(RECEPTION_OF_CITIZENS);
        row5.add(SAMPLE_DOCUMENTS);
        row5.add(HOW_TO_BEHAVE_IN_COURT);

        List<KeyboardRow> keyboardRows = List.of( row2, row4, row5);

        return buildMessage(chatId, textToSend, keyboardRows);
    }

    public SendMessage getSearchCourtCases(long chatId, String textToSend) {

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        KeyboardRow row4 = new KeyboardRow();
        KeyboardRow row5 = new KeyboardRow();

        row1.add(PERSON_NAME);
        row1.add(COMPANY_NAME);
        row2.add(CASE_NUMBER);
        row2.add( JUDGE);
        row3.add(PERIOD_MAKING_FROM);
        row3.add(COURT_FORM_LABEL);
        row4.add(PERIOD_MAKING_TO);
        row4.add(KEYWORDS_IN_JUDGMENT);
        row5.add(RETURN_TO_MAIN_MENU);

        List<KeyboardRow> keyboardRows = List.of( row5, row1,row2, row3, row4);

        return buildMessage(chatId, textToSend, keyboardRows);
    }

    public SendMessage getCourtPractice(long chatId, String textToSend) {

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow row = new KeyboardRow();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();

        row.add(RETURN_TO_MAIN_MENU);
        row1.add(GRAND_CHAMBER_SUPREME_COURT_DIGESTS);
        row1.add(REVIEWS_CASSATION_COURTS);
        row2.add(ECHR_DECISIONS_REVIEWS);
        row2.add(EU_COURT_PRACTICE_REVIEWS);

        List<KeyboardRow> keyboardRows = List.of( row, row1,row2);

        return buildMessage(chatId, textToSend, keyboardRows);
    }

    public SendMessage getGrandChamber(long chatId, String textToSend) {

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow row = new KeyboardRow();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();

        row.add(RETURN_TO_COURT_PRACTICE);
        row1.add(PERIODIC_REVIEWS_GRAND_CHAMBER);
        row2.add(THEMATIC_REVIEWS_GRAND_CHAMBER);

        List<KeyboardRow> keyboardRows = List.of( row, row1,row2);

        return buildMessage(chatId, textToSend, keyboardRows);
    }

    public SendMessage getReviewsCassation(long chatId, String textToSend) {

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow row = new KeyboardRow();
        KeyboardRow row1 = new KeyboardRow();

        row.add(RETURN_TO_COURT_PRACTICE);
        row1.add(PERIODIC_REVIEWS_CASSATION_COURTS);
        row1.add(THEMATIC_REVIEWS_CASSATION_COURTS);

        List<KeyboardRow> keyboardRows = List.of( row, row1);
        return buildMessage(chatId, textToSend, keyboardRows);
    }

    public SendMessage getECHRDecisionsReviews(long chatId, String textToSend) {

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow row = new KeyboardRow();
        KeyboardRow row1 = new KeyboardRow();

        row.add(RETURN_TO_COURT_PRACTICE);
        row1.add(PERIODIC_REVIEWS_ECHR);
        row1.add(THEMATIC_REVIEWS_ECHR);

        List<KeyboardRow> keyboardRows = List.of( row, row1);
        return buildMessage(chatId, textToSend, keyboardRows);
    }

    public SendMessage getPeriodicCassation(long chatId, String textToSend) {

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow row = new KeyboardRow();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();

        row.add(RETURN_TO_PERIODIC_REVIEWS_GRAND_CHAMBER);
        row1.add(CASSATION_ADMINISTRATIVE_COURT);
        row1.add(CASSATION_CIVIL_COURT);
        row2.add(CASSATION_COMMERCIAL_COURT);
        row2.add( CASSATION_CRIMINAL_COURT);

        List<KeyboardRow> keyboardRows = List.of( row, row1,row2);

        return buildMessage(chatId, textToSend, keyboardRows);
    }

    public SendMessage getThematicCassation(long chatId, String textToSend) {

        KeyboardRow row = new KeyboardRow();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();

        row.add(RETURN_TO_PERIODIC_REVIEWS_GRAND_CHAMBER);
        row1.add(THEMATIC_ADMINISTRATIVE_COURT);
        row1.add(THEMATIC_CIVIL_COURT);
        row2.add(THEMATIC_COMMERCIAL_COURT);
        row2.add( THEMATIC_CRIMINAL_COURT);

        List<KeyboardRow> keyboardRows = List.of( row, row1,row2);

        return buildMessage(chatId, textToSend, keyboardRows);
    }

    public SendMessage getCourtForm (long chatId, String textToSend) {

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();

        row1.add(CourtForm.ADMINISTRATIVE.getLabel());
        row1.add(CourtForm.CIVIL.getLabel());
        row2.add(CourtForm.COMMERCIAL.getLabel());
        row2.add(CourtForm.CRIMINAL.getLabel());

        List<KeyboardRow> keyboardRows = List.of( row1,row2 );

        return buildMessage(chatId, textToSend, keyboardRows);
    }

    public SendMessage addSearchButton(SendMessage sendMessage) {

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();

        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText("Пошук");
        inlineKeyboardButton1.setCallbackData(SEARCHING_CASES);

        inlineKeyboardButton2 .setText("Новий пошук");
        inlineKeyboardButton2 .setCallbackData(NEW_SEARCHING_CASES);

        rowInline1.add(inlineKeyboardButton1);
        rowInline1.add(inlineKeyboardButton2);
        // Set the keyboard to the markup
        rowsInline.add(rowInline1);
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        sendMessage.setReplyMarkup(markupInline);
        return sendMessage;
    }

    public SendMessage addActionButton(SendMessage sendMessage, String nameAction, String action) {

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();

        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText(nameAction);
        inlineKeyboardButton1.setCallbackData(action);

        rowInline1.add(inlineKeyboardButton1);
        // Set the keyboard to the markup
        rowsInline.add(rowInline1);
        markupInline.setKeyboard(rowsInline);
        sendMessage.setReplyMarkup(markupInline);
        return sendMessage;
    }

    public SendMessage addNextButton(SendMessage sendMessage) {

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton1  = new InlineKeyboardButton();
        inlineKeyboardButton1 .setText("Наступні");
        inlineKeyboardButton1 .setCallbackData(NEXT_CASES);

        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2 .setText("Новий пошук");
        inlineKeyboardButton2 .setCallbackData(NEW_SEARCHING_CASES);

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow1.add(inlineKeyboardButton2);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        // Set the keyboard to the markup
        rowList.add(keyboardButtonsRow1);
        // Add it to the message
        markupInline.setKeyboard(rowList);
        sendMessage.setReplyMarkup(markupInline);
//        sendMessage.enableMarkdown(true);

        return sendMessage;
    }

    public SendMessage getCourtCasesAnJudicialPracticeMenu(long chatId, String textToSend) {

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();

        row.add(SEARCH_COURT_CASES);
        row2.add(COURT_PRACTICE);
        row3.add(RETURN_TO_MAIN_MENU);

        keyboardRows.add(row);
        keyboardRows.add(row2);
        keyboardRows.add(row3);

        return buildMessage(chatId, textToSend, keyboardRows);
    }

    public SendMessage getHelpMenu(long chatId, String textToSend) {

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();

        row1.add(ELECTRONIC_COURT);
        row1.add(RECEPTION_OF_CITIZENS);
        row2.add(SAMPLE_DOCUMENTS);
        row2.add(HOW_TO_BEHAVE_IN_COURT);
        row3.add(RETURN_TO_MAIN_MENU);

        List<KeyboardRow> keyboardRows = List.of(row3, row1, row2 );
        return buildMessage(chatId, textToSend, keyboardRows);
    }

    public SendMessage getUnsubscribeMessage(Long chatId, String textToSend, String id) throws TelegramApiException {
        return addUnsubscribeButton(buildMessage(chatId, textToSend), id);
    }

    public SendMessage buildMessage(Long chatId, String textToSend) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.enableHtml(true);
        message.setText("<b>" + textToSend + "</b>");
        return message;
    }

    public SendMessage buildMessage(long chatId, String textToSend, List<KeyboardRow> keyboardRows) {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.enableHtml(true);

        message.setText(textToSend);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(keyboardRows);
        keyboardMarkup.setResizeKeyboard(true);

        message.setReplyMarkup(keyboardMarkup);

        return message;
    }


    public SendMessage getMessage(long chatId, String textToSend) {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.enableHtml(true);

        message.setText("<b>" + textToSend + "</b>");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("start");
        row.add("help");
        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);
        keyboardMarkup.setResizeKeyboard(true);

        message.setReplyMarkup(keyboardMarkup);

        return message;
    }

    public SendMessage getPrepareAndSendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        log.info("Replied to chatId {}, message: {}", chatId, message);
        return message;
    }

    public SendMessage getRegister(long chatId) {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Do you really want to register?");

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        InlineKeyboardButton yesButton = new InlineKeyboardButton();

        yesButton.setText("Yes");
        yesButton.setCallbackData(YES_BUTTON);

        InlineKeyboardButton noButton = new InlineKeyboardButton();

        noButton.setText("No");
        noButton.setCallbackData(NO_BUTTON);

        rowInLine.add(yesButton);
        rowInLine.add(noButton);

        rowsInLine.add(rowInLine);

        markupInLine.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInLine);

        return message;
    }

}
