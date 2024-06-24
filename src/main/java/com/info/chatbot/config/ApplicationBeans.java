package com.info.chatbot.config;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ApplicationBeans {

    private final ModelMapperConfig modelMapperConfig;

    @Autowired
    public ApplicationBeans(ModelMapperConfig modelMapperConfig

    ) {

        this.modelMapperConfig = modelMapperConfig;

    }

    @Bean
    public ModelMapper modelMapper() {
        return modelMapperConfig.getModelMapper();
    }




}
