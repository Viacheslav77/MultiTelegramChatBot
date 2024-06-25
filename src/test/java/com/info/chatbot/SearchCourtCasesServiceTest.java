package com.info.chatbot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.info.chatbot.dto.CourtCase;
import com.info.chatbot.dto.DocumentFound;
import com.info.chatbot.entity.Subscribe;
import com.info.chatbot.service.WebClientService;
import com.info.chatbot.service.imp.SearchCourtCasesServiceImp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class SearchCourtCasesServiceTest {


    @Mock
    private WebClientService webClientService;

    @InjectMocks
    private SearchCourtCasesServiceImp searchCourtCasesService;

    @BeforeEach
    void setUp() {
        searchCourtCasesService = new SearchCourtCasesServiceImp(webClientService);
        searchCourtCasesService.setNewCourtCase(4343243L);
    }


    @Test
    public void testSetValueToCourtCase() {
        CourtCase courtCase = new CourtCase(12345L);
        searchCourtCasesService.setCourtCase(courtCase);

        String result = searchCourtCasesService.setValueToCourtCase("keywordsInJudgment", "testKeywords");
        assertEquals("testKeywords", courtCase.getKeywordsInJudgment());
        assertEquals(courtCase.toString(), result);
    }

    @Test
    public void testSaveSubscribeClient() {
        List<DocumentFound> documentFounds = new ArrayList<>();
        DocumentFound documentFound = new DocumentFound();
        documentFound.setDecisionNumber("123");
        documentFounds.add(documentFound);

        searchCourtCasesService.setLastDocumentFounds(documentFounds);
        searchCourtCasesService.setLastCaseNumber("001");

        Subscribe result = searchCourtCasesService.saveSubscribeClient();

        assertNotNull(result);
        assertEquals("001", result.getCaseNumber());
        assertEquals(Arrays.asList("123"), result.getDecisionNumbers());
    }

    @Test
    public void testSearchByCourtCase() {
        WebDriver mockDriver = mock(WebDriver.class);
        WebElement mockElement = mock(WebElement.class);

        when(webClientService.parseCourtGovUaHTML()).thenReturn(mockDriver);
        when(mockDriver.findElement(By.id("btn"))).thenReturn(mockElement);
        doNothing().when(mockElement).click();

        String result = searchCourtCasesService.searchingByCourtCase();

        assertNotNull(result);
        verify(mockDriver, times(1)).quit();
    }

}
