package com.info.chatbot.entity;


import java.sql.Timestamp;

public class User {

    private Long chatId;

    private boolean isStopSendingMessages;

    private String telegramВotName;

    private boolean isAuthorized;

    private String firstName;

    private String lastName;

    private String userName;

    private String lastCodeSent;

    private Timestamp registeredAt;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Timestamp getRegisteredAt() {
        return registeredAt;
    }

    public String getLastCodeSent() {
        return lastCodeSent;
    }

    public void setLastCodeSent(String lastCodeSent) {
        this.lastCodeSent = lastCodeSent;
    }

    public void setRegisteredAt(Timestamp registeredAt) {
        this.registeredAt = registeredAt;
    }

    public boolean isStopSendingMessages() {
        return isStopSendingMessages;
    }

    public void setStopSendingMessages(boolean stopSendingMessages) {
        isStopSendingMessages = stopSendingMessages;
    }

    @Override
    public String toString() {
        return "User{" +
                "chatId=" + chatId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", registeredAt=" + registeredAt +
                '}';
    }
}
