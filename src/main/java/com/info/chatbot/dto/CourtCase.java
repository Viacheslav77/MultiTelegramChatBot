package com.info.chatbot.dto;

import com.info.chatbot.constants.CourtForm;
import lombok.Data;


import java.util.List;

import static com.info.chatbot.constants.MessageText.*;

@Data
public class CourtCase {

    public static List<String> fieldsName = List.of(
            "keywordsInJudgment", "personName", "companyName", "caseNumber", "courtForm", "judge","periodMakingFrom","periodMakingTo");

    private String keywordsInJudgment;
    private String hrefToDoc;
    private String personName;
    private String companyName;
    private String caseNumber;
    private CourtForm courtForm;
    private String judge;
    private String periodMakingFrom;
    private String periodMakingTo;

    private long chatId;

    public CourtCase() {

    }

    public CourtCase(long chatId) {
        this.chatId = chatId;
    }

    @Override
    public String toString() {
        return "Встановлені такі крітерії пошуку: \n\n" +
                putToText(KEYWORDS_IN_JUDGMENT, keywordsInJudgment) +
                putToText(PERSON_NAME, personName) +
                putToText(COMPANY_NAME, companyName) +
                putToText(CASE_NUMBER, caseNumber) +
                (courtForm != null ? putToText(COURT_FORM_LABEL, courtForm.getLabel()):"") +
                putToText(JUDGE, judge) +
                putToText(PERIOD_MAKING_FROM, periodMakingFrom) +
                putToText(PERIOD_MAKING_TO, periodMakingTo) + "\n" +
                "----------------------------";

    }
    private String putToText(String constanta, String text) {
        return text != null ?  " - " + constanta + ": " + "<b>" + text + "</b>" + " \n " :"";
    }
}
