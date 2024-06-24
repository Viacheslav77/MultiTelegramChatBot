package com.info.chatbot.service;

import com.info.chatbot.entity.Subscribe;
import org.jvnet.hk2.annotations.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface SubscribeService {

    Optional<Subscribe> findOneByCaseNumber(String caseNumber);

    void deleteByCaseNumber(String caseNumber);

    Subscribe create(Subscribe signed);

    Subscribe readById(long id);

    void delete(long id);

    List<Subscribe> getAll();
}
