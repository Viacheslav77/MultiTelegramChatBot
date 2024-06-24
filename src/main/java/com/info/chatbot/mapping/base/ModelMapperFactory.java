package com.info.chatbot.mapping.base;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperFactory {

  private static ModelMapper modelMapper;

  @Autowired
  public ModelMapperFactory(ModelMapper modelMapper) {
    ModelMapperFactory.modelMapper = modelMapper;
  }

  static ModelMapper get() {
    return modelMapper;
  }
}
