package com.info.chatbot.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "signed")
@Data
@Accessors(chain = true)
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "chat_id", nullable = false)
    private long chatId;

    @Column(name = "bot_name", nullable = false)
    private String botName;

    @Column(name = "case_number", nullable = false)
    private String caseNumber;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "decision_numbers", nullable = false)
    private List<String> decisionNumbers;

    public Subscribe() {
    }

    public static Subscribe buildSignedUp(String caseNumber, List<String>  decisionNumbers, String botName) {

        return new Subscribe()
                .setCaseNumber(caseNumber)
                .setBotName(botName)
                .setIsActive(true)
                .setCreatedAt(LocalDateTime.now())
                .setDecisionNumbers(decisionNumbers);
    }

    @Override
    public String toString() {
        return "SignedUp{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", caseNumber='" + caseNumber + '\'' +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", decisionNumbers='" + decisionNumbers + '\'' +
                '}';
    }
}
