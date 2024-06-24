package com.info.chatbot.specification;

import com.info.chatbot.entity.Subscribe;
import com.info.chatbot.entity.Subscribe_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;


@Component
public class SubscribeSpecification extends BaseSpecification {

  public Specification<Subscribe> findOneByCaseNumber(  String caseNumber ) {
    return entityFieldEqualsParam (Subscribe_.caseNumber, caseNumber);
  }

}
