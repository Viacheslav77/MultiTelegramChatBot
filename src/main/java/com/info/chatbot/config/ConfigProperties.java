package com.info.chatbot.config;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Properties;

@Slf4j
public class ConfigProperties {

	private static ConfigProperties instanceConfigProperties;
	private static Properties properties;
	private ConfigProperties() {

		properties = new Properties();

		try {
			String path = System.getProperty("user.dir") + "/" + "config" + "/"	+ "application.properties";

			log.info("pathProperty = " + path);
			File initialFile = new File(path);
			InputStream targetStream = new FileInputStream(initialFile);
			properties.load(targetStream);

			log.info("Service description:" + properties.getProperty("monitor.service.description"));

		} catch (IOException e) {
			log.error("ConfigProperties IOException: " + e);
		}
	}
	
	public static synchronized Properties getPropertiesInstance() {

		if (instanceConfigProperties == null) {
			instanceConfigProperties = new ConfigProperties();
			log.info("Created new ConfigProperties instance.");
			return ConfigProperties.properties;
		} else {
			//LOG.info("Getting exist ConfigProperties instance.");
			return ConfigProperties.properties;
		}
	}
}
