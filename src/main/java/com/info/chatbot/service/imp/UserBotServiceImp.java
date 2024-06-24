package com.info.chatbot.service.imp;


import com.info.chatbot.entity.User;
import com.info.chatbot.service.UserBotService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Data
@Service
public class UserBotServiceImp implements UserBotService {

    private Map<String, User> users = new HashMap<>();

    @Override
    public void registerUser(Message msg) {

        Optional<User> optionalUser = Optional.ofNullable(users.get(msg.getChatId().toString()));

        if(!optionalUser.isPresent()){

            Long chatId = msg.getChatId();
            Chat chat = msg.getChat();

            User user = new User();

            user.setChatId(chatId);
            user.setFirstName(chat.getFirstName());
            user.setLastName(chat.getLastName());
            user.setUserName(chat.getUserName());
            user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));

            users.put(chatId.toString(), user);
            log.info("user saved: " + user);
        }
    }

    @Override
    public void setStatusSendMess (Long chatId, boolean status){

        Optional<User> optionalUser = Optional.ofNullable(users.get(chatId.toString()));

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setStopSendingMessages(status);
            users.put(chatId.toString(), user);
        }
    }
    @Override
    public void addUser(String key, User user) {
        users.put(key, user);
    }



}
