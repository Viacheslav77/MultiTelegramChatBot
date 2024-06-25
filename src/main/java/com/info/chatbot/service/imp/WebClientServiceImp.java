package com.info.chatbot.service.imp;

import com.info.chatbot.config.WebClientConfig;
import com.info.chatbot.service.WebClientService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class WebClientServiceImp implements WebClientService {

    private final WebClientConfig webClientConfig;

    public WebClientServiceImp(WebClientConfig webClientConfig) {
        this.webClientConfig = webClientConfig;
    }

    @Override
    public WebDriver parseCourtGovUaHTML() {
        WebDriver driver = new ChromeDriver();
        driver.get(webClientConfig.getCourtGovUaUrl());
        return driver;
    }

    @Override
    public WebDriver parseCourtGovUaAnalisesHTML() {
        WebDriver driver = new ChromeDriver();
        driver.get(webClientConfig.getCourtGovUaAnalizUrl());
        return driver;
    }

    @Override
    public void setValueByName(WebDriver driver, String nameElement, String value) {
        WebElement inputRegDateBegin = driver.findElement(By.name(nameElement));
        inputRegDateBegin.click();
        inputRegDateBegin.sendKeys(value);
    }

    @Override
    public <T> List<T> parseTable(WebElement table, Function<List<String>, T> rowMapper) {

        List<T> tableData = new ArrayList<>();
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            List<String> rowData = new ArrayList<>();
            for (WebElement cell : cells) {
                rowData.add(cell.getText());
                if (rowData.size() == 1) {
                    rowData.add(cell.findElement(By.tagName("a")).getAttribute("href"));
                }
            }
            if (!rowData.isEmpty()) {
                tableData.add(rowMapper.apply(rowData));
            }

        }
        return tableData;
    }

}
