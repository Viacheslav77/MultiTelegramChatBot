package com.info.chatbot.service.imp;

import com.info.chatbot.constants.CourtForm;
import com.info.chatbot.dto.CourtCase;
import com.info.chatbot.dto.DocumentFound;
import com.info.chatbot.entity.Subscribe;
import com.info.chatbot.service.SearchCourtCasesService;
import com.info.chatbot.service.WebClientService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.info.chatbot.constants.MessageText.*;

@Data
@Controller
@Slf4j
public class SearchCourtCasesServiceImp implements SearchCourtCasesService {

    private final WebClientService webClientService;

//    private final WebClientConfig config = new WebClientConfig();
//    private final WebClientService webClientService = new WebClientServiceImp(config);


    private final List<String> COURT_CASE_CONSTANTS = List.of(
            KEYWORDS_IN_JUDGMENT,
            PERSON_NAME,
            COMPANY_NAME,
            CASE_NUMBER,
            JUDGE,
            PERIOD_MAKING_FROM,
            PERIOD_MAKING_TO
    );

    private CourtCase courtCase;
    private List<DocumentFound> lastSearching = new ArrayList<>();
    private List<DocumentFound> lastDocumentFounds = new ArrayList<>();
    private String lastCaseNumber;
    private int countShowedDocs = 0;
    private int countShowDocResults = 3;

    @Override
    public List<String> getCOURT_CASE_CONSTANTS() {
        return COURT_CASE_CONSTANTS;
    }

    @Override
    public String checkCourtCase(String messageText, Update update) {

        String expectedValue = null;

     if (messageText.equals(KEYWORDS_IN_JUDGMENT)) {
            expectedValue = "keywordsInJudgment";
        } else if (messageText.equals(PERSON_NAME)) {
            expectedValue = "personName";
        } else if (messageText.equals(COMPANY_NAME)) {
            expectedValue = "companyName";
        } else if (messageText.equals(CASE_NUMBER)) {
            expectedValue = "caseNumber";
        } else if (messageText.equals(COURT_FORM_LABEL)) {
            expectedValue = "courtForm";
        } else if (messageText.equals(CourtForm.CRIMINAL.getLabel())) {
         expectedValue = "courtFormCriminal";
        } else if (messageText.equals(CourtForm.ADMINISTRATIVE.getLabel())) {
            expectedValue = "courtFormAdmin";
        } else if (messageText.equals(CourtForm.CIVIL.getLabel())) {
            expectedValue = "courtFormCivil";
        } else if (messageText.equals(CourtForm.COMMERCIAL.getLabel())) {
            expectedValue = "courtFormCommercial";
        } else if (messageText.equals(JUDGE)) {
            expectedValue = "judge";
        } else if (messageText.equals(PERIOD_MAKING_FROM)) {
         expectedValue = "periodMakingFrom";
        } else if (messageText.equals(PERIOD_MAKING_TO)) {
         expectedValue = "periodMakingTo";
        } else  {
            log.error("Команда не була розпізнана ");
        }
        return expectedValue;
    }

    @Override
    public String setValueToCourtCase(String expectedValue, String messageText) {

        if (expectedValue.equals("keywordsInJudgment")) {
            courtCase.setKeywordsInJudgment(messageText);
        } else if (expectedValue.equals("personName")) {
             courtCase.setPersonName(messageText);
        } else if (expectedValue.equals("companyName")) {
             courtCase.setCompanyName(messageText);
        } else if (expectedValue.equals("caseNumber")){
            courtCase.setCaseNumber(messageText);
        } else if (expectedValue.equals("judge")){
             courtCase.setJudge(messageText);
        } else if (expectedValue.equals("courtForm")){
            courtCase.setCourtForm(
                    Arrays.stream(CourtForm.values())
                            .filter(e -> e.getLabel().equalsIgnoreCase(messageText))
                            .findFirst()
                            .get()
            );
        } else  if (expectedValue.equals("periodMakingFrom")){;
            courtCase.setPeriodMakingFrom(messageText);
        } else  if (expectedValue.equals("periodMakingTo")){;
            courtCase.setPeriodMakingTo(messageText);
        } else {
            log.error("Команда не була розпізнана ");
            return "Команда не була розпізнана ";
        }
        return courtCase.toString();
    }

    @Override
    public Subscribe saveSubscribeClient() {
        Subscribe subscribe = null;
        try {
            List<String> lastDecisionNumber = lastDocumentFounds.stream().map(DocumentFound::getDecisionNumber).toList();
            subscribe = Subscribe.buildSignedUp(lastCaseNumber, lastDecisionNumber);

        } catch (Exception e) {
            log.error("Error lastCaseNumber and lastDocumentFounds");
            throw new RuntimeException("Error lastCaseNumber and lastDocumentFounds");
        }
        return subscribe;
    }

    @Override
    public void setNewCourtCase(long chatId) {
        courtCase = new CourtCase(chatId);
        lastSearching.clear();
        countShowedDocs = 0;
    }

    @Override
    public  String getLastCaseNumber() {
        return lastCaseNumber;
    }

    @Override
    public  CourtCase getCourtCase() {
        return courtCase;
    }

    @Override
    public long getActualChatId() {
        return courtCase.getChatId();
    }

    @Override
    public String searchingByCaseNumber(String caseNumber) {
        countShowedDocs = lastSearching.size();
//        courtCase = new CourtCase(courtCase.getChatId());
        courtCase.setCaseNumber(caseNumber);
        return searchingByCourtCase();
    }

    @Override
    public String getCourtCaseById(int id) {
        return lastSearching.stream().filter(c -> c.getId() == id).findFirst().get().getCaseNumber();
    }

    @Override
    public String searchingFirstByCourtCase() {
        lastSearching.clear();
        countShowedDocs = 0;
        return searchingByCourtCase();
    }

    @Override
    public List<Subscribe> searchingNewCases(List<Subscribe> subscribes) {

        List<Subscribe> callToChats = new ArrayList<>();

        for (Subscribe item : subscribes){
            courtCase = new CourtCase();
            lastSearching.clear();
            countShowedDocs = 0;
            searchingByCourtCase();
            //-1, це помилка для тесту
            if (lastDocumentFounds.size() != item.getDecisionNumbers().size() - 1) {
                callToChats.add(item);
            }
        }
        return callToChats;
    }

    @Override
    public String searchingByCourtCase() {

        WebDriver courtGovUaHTML = webClientService.parseCourtGovUaHTML();
        List<DocumentFound> listDocumentFounds = new ArrayList<>();
        String searchExpression = "";
        int countSearchResult = 0;

        try {
            if (courtCase.getKeywordsInJudgment() != null) {
                searchExpression = searchExpression + courtCase.getKeywordsInJudgment();
            }
            if (courtCase.getPersonName() != null) {
                searchExpression = searchExpression + courtCase.getPersonName();
            }
            if (courtCase.getCompanyName() != null) {
                searchExpression = searchExpression + courtCase.getCompanyName();
            }
            if (courtCase.getCaseNumber() != null) {
                webClientService.setValueByName(courtGovUaHTML, "CaseNumber", courtCase.getCaseNumber());
            }
            if (courtCase.getCourtForm() != null) {
                courtGovUaHTML.findElements(By.id("CSType")).get(0).click();
                List<WebElement> checkboxes = courtGovUaHTML.findElements(By.name("CSType[]"));
                checkboxes.get(courtCase.getCourtForm().getNumber()).click();
                courtGovUaHTML.findElements(By.id("myFieldSet_3")).get(0).findElement(By.cssSelector(".tdOk")).click();
            }
            if (courtCase.getJudge() != null) {
                webClientService.setValueByName(courtGovUaHTML, "ChairmenName", courtCase.getJudge());
            }
            if (courtCase.getPeriodMakingFrom() != null) {
                webClientService.setValueByName(courtGovUaHTML, "RegDateBegin", courtCase.getPeriodMakingFrom());
            }
            if (courtCase.getPeriodMakingTo() != null) {
                webClientService.setValueByName(courtGovUaHTML, "RegDateEnd", courtCase.getPeriodMakingTo());
            }
            if (!searchExpression.isEmpty()) {
                webClientService.setValueByName(courtGovUaHTML, "SearchExpression", searchExpression);
            }
            WebElement submitButton = courtGovUaHTML.findElement(By.id("btn"));
            submitButton.click();
            String divFooterSearch = courtGovUaHTML.findElement(By.id("divFooterSearch")).getText();
            String key = "За заданими параметрами пошуку знайдено документів:";

            if (divFooterSearch.contains(key)) {
                countSearchResult = Integer.parseInt(divFooterSearch.replace(key,"").trim());
                WebElement table = courtGovUaHTML.findElement(By.id("divresult")).findElement(By.tagName("table"));
                listDocumentFounds = webClientService.parseTable(table, (DocumentFound::new));
            } else {
                return "За заданими параметрами пошуку не знайдено жодного документу.";
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            courtGovUaHTML.quit();
        }
        lastCaseNumber = courtCase.getCaseNumber();
        lastDocumentFounds = listDocumentFounds;

        lastSearching.addAll(listDocumentFounds);
        return "За заданими параметрами пошуку знайдено " +  countSearchResult  +" документів" + "\n"
                +"---------------------------------\n\n"
                + getLastSearching();
    }

    @Override
    public int getCountShowDocResults() {
        return countShowDocResults + countShowedDocs;
    }
    @Override
    public int getSizeLastSearching() {
        return lastSearching.size();
    }

    @Override
    public String getLastSearching(){

        StringBuilder result = new StringBuilder();

        int start = countShowedDocs;
        int end = countShowDocResults + countShowedDocs;

        if (!lastSearching.isEmpty()) {
            for (int i = start; i < end; i++) {
                try {
                    DocumentFound show = lastSearching.get(i);
                    if (show != null) {
                        result.append(show);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
            countShowedDocs = countShowedDocs + countShowDocResults;
        }
        return result.toString();
    }


}
