package com.info.chatbot;

import com.info.chatbot.factory.MenuBotBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MenuBotBuilderTest {

    @InjectMocks
    private MenuBotBuilder menuBotBuilder;

    @Test
    void getMainMenu_shouldReturnCorrectMessageWithKeyboardRows() {
        String chatId = "123";
        String textToSend = "Main menu";

        SendMessage result = menuBotBuilder.getMainMenu(Integer.parseInt(chatId), textToSend);
        assertEquals(chatId, result.getChatId());
        assertEquals(textToSend, result.getText());

        ReplyKeyboardMarkup keyboardMarkup = (ReplyKeyboardMarkup) result.getReplyMarkup();
        List<KeyboardRow> keyboardRows = keyboardMarkup.getKeyboard();
        assertEquals(5, keyboardRows.size());
    }

}

