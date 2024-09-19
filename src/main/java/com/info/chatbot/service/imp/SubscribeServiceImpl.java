package com.info.chatbot.service.imp;


import com.info.chatbot.entity.Subscribe;
import com.info.chatbot.mapping.base.BaseMapper;
import com.info.chatbot.service.SubscribeService;
import com.info.chatbot.specification.SubscribeSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.info.chatbot.repository.SubscribeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Scope("prototype")
public class SubscribeServiceImpl implements SubscribeService, BaseMapper {

    private SubscribeRepository repository;
    private SubscribeSpecification specification;

    @Autowired
    public void setSubscribeSpecification(SubscribeSpecification specification){
        this.specification = specification;
    }
    @Autowired
    public void setSignedRepository(SubscribeRepository subscribeRepository){
        this.repository = subscribeRepository;
    }

    @Override
    public Optional<Subscribe> findOneByCaseNumber(String caseNumber) {
        return repository.findOne(specification.findOneByCaseNumber(caseNumber));
    }

    @Override
    public void deleteByCaseNumber(String caseNumber) {
        Optional<Subscribe> signed = findOneByCaseNumber(caseNumber);
        if (signed.isEmpty()) {
            log.error("Does not find by CaseNumber: " + caseNumber);
            return;
        }
        repository.delete(signed.get());
    }

    @Override
    public Subscribe create(Subscribe signed) {
            return repository.save(signed);
    }

    @Override
    public Subscribe readById(long id) {
        Optional<Subscribe> optional = repository.findById(id);
            return optional.get();
    }

    @Override
    public void delete(long id) {
        Subscribe signed = readById(id);
        repository.delete(signed);
    }

    @Override
    public List<Subscribe> getAll() {
        List<Subscribe> signeds = repository.findAll();
        return signeds.isEmpty() ? new ArrayList<>() : signeds;
    }
}
