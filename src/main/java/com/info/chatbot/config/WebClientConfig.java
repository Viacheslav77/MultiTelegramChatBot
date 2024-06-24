package com.info.chatbot.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@Data
@Accessors(chain = true)
public class WebClientConfig {

    Properties properties = ConfigProperties.getPropertiesInstance();

//    @Value("${court.gov.ua.url}")
    String courtGovUaUrl = properties.getProperty("url.court.gov.ua");

    String courtGovUaAnalizUrl = properties.getProperty("url.court.gov.ua.analiz");

//    @Value("${webdriver.type}")
    String webdriverType = properties.getProperty("webdriver.type");

//    @Value("${webdriver.path}")
    String webdriverPath = properties.getProperty("webdriver.path");

}
