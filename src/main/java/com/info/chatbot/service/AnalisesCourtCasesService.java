package com.info.chatbot.service;


import org.jvnet.hk2.annotations.Service;

@Service
public interface AnalisesCourtCasesService {

    String parsingItemAnalises(int countParse);

    String buildTextElectronicCourt();

    String buildTextReseptionOfCitizen();

    String buildTextSampleDocuments();

    String buildTextBehaveToCort();
}
