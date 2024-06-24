package com.info.chatbot.factory;

import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Chat;

@Slf4j
public class MessageBotBuilder {

    public static final String YES_BUTTON = "YES_BUTTON";
    public static final String NO_BUTTON = "NO_BUTTON";
    public static final String ERROR_TEXT = "Error occurred: ";

    public static String getFinalTextToSend(String textToSend, int direct) {

        if (direct == 0) {
            textToSend = ":red_circle:" + ":x:" + textToSend;
        } else {
            textToSend = ":white_check_mark:" + textToSend;
        }
        return EmojiParser.parseToUnicode(textToSend);
    }

    public static String prepareTextToSend(String typeMessage, String subject, String text) {

        String textToSend = subject + " / " + text;

        if (typeMessage.equals("alarm")) {
            textToSend = ":red_circle:" + ":x:  " + textToSend;
        } else {
            textToSend = ":white_check_mark:  " + textToSend;
        }
        return EmojiParser.parseToUnicode(textToSend);
    }

    public static String getHelpText() {
        return "Цей бот створений для показу повідомлень.\n\n" +
                "Ви можете використовувати наступні команди, в командній строці, чи з лівого або нижнього меню :\n\n" +
                "Для початку роботи - /start \n\n" +
//              "Для надіслання повідомлень - /start_sending_alerts\"\n\n" +
//              "Для зупинки надсилання повідомлень - /stop_sending_alerts\n\n" +
//              "Посилання на цей бот - /myURL\n\n" +
                "Для допомоги  - /help";
    }

    public static String getStartMessage(long chatId, String name, String botName) {
        return EmojiParser.parseToUnicode("Вітаю, " + name + ", приемно познайомитися! " +
                "Я " + botName + ". В режимі онлайн буду надсилати тобі повідомлення" +" :blush:");
    }

    public static String getChatIdReceived(Chat chat, String name, String botName) {

        String answer = EmojiParser.parseToUnicode("Привіт, " + name + ", ти підключив " + botName
                + " до нової групи '" + chat.getTitle() + "' з chatId: " + chat.getId() +" :blush:");
        log.info(answer);

        return answer;
    }

    public static String getStartCommandReceived(String name, String botName) {

        String answer = EmojiParser.parseToUnicode("Привіт, " + name + ", приемно познайомитися! Я " + botName + ". В режимі онлайн буду надсилати тобі повідомлення" +" :blush:");
        log.info("Replied to user " + name);

        return answer;
    }

}
