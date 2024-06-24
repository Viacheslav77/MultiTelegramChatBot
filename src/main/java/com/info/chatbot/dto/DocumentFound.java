package com.info.chatbot.dto;

import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Random;

@Data
public class DocumentFound {

    private int id;
    private String decisionNumber;
    private String hrefToDoc;
    private String decisionForm;
    private String decisionDate;
    private String effectiveDate;
    private String proceedingForm;
    private String caseNumber;
    private String courtName;
    private String judgeName;

    public DocumentFound() {

    }

    public DocumentFound(List<String> data) {

        this.id = new Random().nextInt();
        if (this.id < 0) {
            this.id = this.id * (-1);
        }
        this.decisionNumber = data.get(0);
        this.hrefToDoc = data.get(1);
        this.decisionForm = data.get(2);
        this.decisionDate = data.get(3);
        this.effectiveDate = data.get(4);
        this.proceedingForm = data.get(5);
        this.caseNumber = data.get(6);
        this.courtName = data.get(7);
        this.judgeName = data.get(8);

    }

    public DocumentFound(String decisionNumber, String decisionForm, String decisionDate, String effectiveDate, String proceedingForm, String caseNumber, String courtName, String judgeName) {
        this.decisionNumber = decisionNumber;
        this.decisionForm = decisionForm;
        this.decisionDate = decisionDate;
        this.effectiveDate = effectiveDate;
        this.proceedingForm = proceedingForm;
        this.caseNumber = caseNumber;
        this.courtName = courtName;
        this.judgeName = judgeName;
    }

    public static DocumentFound crete(List<String> data) {
        return new DocumentFound();
    }

    private String encodeCaseNumberToBase64() {
        return Base64.getUrlEncoder().encodeToString(caseNumber.getBytes());


    }

    public static String decodeBase64ToCaseNumber(String base64) {
        byte[] decodedBytes = Base64.getUrlDecoder().decode(base64);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }

    @Override
    public String toString() {

        return "№ рішення: " + "<a href=\""+ hrefToDoc + "\" >" +  decisionNumber + "</a>" + "\n" +
                "Форма судового рішення: " + decisionForm + "\n" +
                "Дата ухвалення рішення: " + decisionDate + "\n" +
                "Дата набрання законної сили: " + effectiveDate + "\n" +
                "Форма судочинства: " + proceedingForm + "\n" +
                "№ судової справи: " +    caseNumber  + "\n" +
                "Назва суду: " + courtName + "\n" +
                "Суддя: " + judgeName + "\n" +
                "Переглянути рішення по цій справі: /search_court_" + getId() + "\n" +
                "----------------------------------\n";
    }
}