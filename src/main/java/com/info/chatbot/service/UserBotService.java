package com.info.chatbot.service;

import com.info.chatbot.entity.User;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;

@Service
public interface UserBotService {

    void registerUser(Message msg);

    void setStatusSendMess(Long chatId, boolean status);

    Map<String, User> getUsers();

    void addUser(String key, User user);
}