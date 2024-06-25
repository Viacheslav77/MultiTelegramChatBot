package com.info.chatbot;


import com.info.chatbot.entity.Subscribe;
import com.info.chatbot.repository.SubscribeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SubscribeRepositoryTest {
    @Autowired
    private SubscribeRepository taskRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void newTaskTest() {
        Subscribe subscribe = new Subscribe();
        subscribe.setIsActive(true);
        subscribe.setCreatedAt(LocalDateTime.now());
        subscribe.setCaseNumber("Task#100");
        subscribe.setChatId(12233L);
        subscribe.setDecisionNumbers(List.of("326776"));

        taskRepository.save(subscribe);
        long id = subscribe.getId();
        Subscribe actual = taskRepository.getOne(id);
        Assertions.assertEquals("Task#100", actual.getCaseNumber());
    }
}

