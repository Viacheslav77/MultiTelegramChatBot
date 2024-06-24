package com.info.chatbot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExampleApplication.class, args);


//		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//		SearchCourtCasesServiceImp myService = context.getBean(SearchCourtCasesServiceImp.class);
//		log.info("MyService bean obtained: " + myService);
//		myService.someServiceMethod();

	}

}
