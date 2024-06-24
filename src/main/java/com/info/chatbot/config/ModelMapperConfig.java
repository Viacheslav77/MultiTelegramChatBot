package com.info.chatbot.config;

import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Configuration;

@Configuration
@NoArgsConstructor
public class ModelMapperConfig {
  ModelMapper getModelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setAmbiguityIgnored(true);
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//    configureModelMapper(modelMapper);
    return modelMapper;
  }


}
