package com.info.chatbot.service;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.function.Function;

public interface WebClientService {

    WebDriver parseCourtGovUaHTML();

    WebDriver parseCourtGovUaAnalisesHTML();

    void setValueByName(WebDriver driver, String nameElement, String value);

    <T> List<T> parseTable(WebElement table, Function<List<String>, T> rowMapper);
}
