package com.info.chatbot.service.imp;

import com.info.chatbot.constants.SupremeCourtInfo;
import com.info.chatbot.dto.AnalisesParsed;
import com.info.chatbot.service.AnalisesCourtCasesService;
import com.info.chatbot.service.WebClientService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.info.chatbot.constants.MessageText.*;
import static com.info.chatbot.constants.SupremeCourtInfo.*;

@Data
@Controller
@Slf4j
@Scope("prototype")
public class AnalisesCourtCasesServiceImp implements AnalisesCourtCasesService {

    private int limit = 10;
    private final WebClientService webClientService;

    private List<String> keysParse = List.of(
            "Періодичні дайджести", "Тематичні дайджести",
            "Касаційний адміністративний суд", "Касаційний господарський суд", "Касаційний кримінальний суд", "Касаційний цивільний суд",
            "Касаційний адміністративний суд", "Касаційний господарський суд", "Касаційний кримінальний суд", "Касаційний цивільний суд",
            "Періодичні огляди", "Тематичні огляди",
            "Огляди практики Суду Європейського Союзу");

    private List<String> keysList = List.of(
            PERIODIC_REVIEWS_GRAND_CHAMBER, THEMATIC_REVIEWS_GRAND_CHAMBER,
            CASSATION_ADMINISTRATIVE, CASSATION_COMMERCIAL, CASSATION_CRIMINAL, CASSATION_CIVIL,
            CASSATION_ADMINISTRATIVE, CASSATION_COMMERCIAL, CASSATION_CRIMINAL, CASSATION_CIVIL,
            PERIODIC_REVIEWS , PERIODIC_REVIEWS,
            EU_COURT_PRACTICE);

    @Override
    public String buildTextElectronicCourt() {
        return formatText(GET_ECOURT_INFO) + "\n\n" +
                formatText(LOGIN_TO_ECOURT_CABINET) + "\n\n" +
                "_____________________________";
    }
    @Override
    public String buildTextReseptionOfCitizen() {
        return formatText(PERSONAL_RECEPTION_SCHEDULE) + "\n\n" +
                formatText(PERSONAL_RECEPTION_ORDER) + "\n\n" + formatText(GET_ECOURT_INFO) + "\n\n" +
                formatText(POSTAL_ADDRESSES) + "\n\n" + formatText(GET_ECOURT_INFO) + "\n\n" +
                formatText(EMAIL_ADDRESSES) + "\n\n" + formatText(GET_ECOURT_INFO) + "\n\n" +
                formatText(ELECTRONIC_RECEPTION) + "\n\n" + formatText(GET_ECOURT_INFO) + "\n\n" +
                formatText(PERSONAL_RECEPTION_REQUEST_SAMPLE) + "\n\n" +
                "_____________________________";
    }
    @Override
    public String buildTextSampleDocuments() {
        return formatText(SAMPLE_DOCUMENTS_E) + "\n\n" +
                "_____________________________";
    }
    @Override
    public String buildTextBehaveToCort() {
        return formatText(HOW_TO_BEHAVE_IN_COURT_E) + "\n\n" +
                "_____________________________";
    }

    private String formatText(SupremeCourtInfo text) {
        return "<a href=\"" + text.getUrl() + "\" >" + text.getTitle() + "</a>";
    }
    @Override
    public String parsingItemAnalises(int countParse) {

        WebDriver courtGovUaHTML = webClientService.parseCourtGovUaAnalisesHTML();
        List<WebElement> liElements = courtGovUaHTML.findElements(By.tagName("li"));
        List<WebElement> clearLiElements = getClearLiList(liElements);
        List<AnalisesParsed> parseds = parseList(clearLiElements.get(countParse), countParse);
        courtGovUaHTML.quit();
        return parseds
                .stream()
                .map(AnalisesParsed::toString)
                .limit(limit)
                .collect(Collectors.joining("\n\n"));

    }

    private String checkTitle(String title, AnalisesParsed analisesParsed, int countKeyPars) {
        if (title.isEmpty()) {
            return keysList.get(countKeyPars) + "\n" +
                    analisesParsed.getHref()
                            .split("/")[analisesParsed
                            .getHref()
                            .split("/")
                            .length - 1];
        }
        return title;
    }

    private List<WebElement> getClearLiList( List<WebElement> elements) {

        List<WebElement> result = new ArrayList<>();
        int countKeyPars = 0;
        for (WebElement element : elements) {

            try {
                if (element.getText().equalsIgnoreCase(keysParse.get(countKeyPars))) {
                    result.add(element);
                    countKeyPars++;
                }
            } catch (Exception e) {
//                log.info(e.getMessage());
            }
        }
        return result;
    }

    private  List<AnalisesParsed> parseList(WebElement element, int countParse) {

        List<AnalisesParsed> analisesParseds = new ArrayList<>();
        try {
            element.click();
        } catch (Exception exception) {
        }
        List<WebElement> elements = element.findElements(By.tagName("a"));
        for (WebElement item : elements) {
            AnalisesParsed analisesParsed = new AnalisesParsed();
            analisesParsed.setHref(item.getAttribute("href"));
            analisesParsed.setTitle(
                    checkTitle(item.findElement(
                            By.className("documents__title")).getText(),
                            analisesParsed,
                            countParse));
            analisesParseds.add(analisesParsed);
        }
       return analisesParseds;
    }
}
