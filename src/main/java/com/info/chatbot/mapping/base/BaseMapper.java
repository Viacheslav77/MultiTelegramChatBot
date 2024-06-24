package com.info.chatbot.mapping.base;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public interface BaseMapper {

  default <S, T> T map(S source, Class<T> targetClass) {
    return ModelMapperFactory.get().map(source, targetClass);
  }

  default <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
    return source
        .stream()
        .map(e -> map(e, targetClass))
        .collect(Collectors.toList());
  }

  default <S, T> Page<T> mapPage(Page<S> source, Class<T> targetClass) {
    return source.map(e -> map(e, targetClass));
  }

}
