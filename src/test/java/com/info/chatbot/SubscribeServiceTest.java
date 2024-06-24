package com.info.chatbot;

import com.info.chatbot.entity.Subscribe;
import com.info.chatbot.exception.NullEntityReferenceException;
import com.info.chatbot.repository.SubscribeRepository;
import com.info.chatbot.service.imp.SubscribeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SubscribeServiceTest {

    @InjectMocks
    private SubscribeServiceImpl service;

    @Mock
    private SubscribeRepository repository;

    private Subscribe subscribeExpected;
    private Subscribe subscribeExpectedSecond;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        subscribeExpected = new Subscribe();
        subscribeExpected.setId(1L);
        subscribeExpected.setCaseNumber("CaseNumber1");
        subscribeExpected.setChatId(343434);

        subscribeExpectedSecond = new Subscribe();
        subscribeExpectedSecond.setId(2L);
        subscribeExpected.setCaseNumber("CaseNumber2");
        subscribeExpectedSecond.setIsActive(true);
    }

    @Test
    final void createSubscribeTest() {
        when(repository.save(subscribeExpected)).thenReturn(subscribeExpected);
        Assertions.assertEquals(subscribeExpected, service.create(subscribeExpected));
    }

    @Test
    final void getSubscribeIdTest() {
        when(repository.findById(anyLong())).thenReturn(java.util.Optional.of(subscribeExpected));
        Subscribe taskActual = service.readById(1L);
        Assertions.assertEquals(343434, taskActual.getChatId());
    }


    @Test
    final void deleteTaskTest() {
        when(repository.findById(anyLong())).thenReturn(java.util.Optional.of(subscribeExpected));
        service.delete(subscribeExpected.getId());
        verify(repository, times(1)).delete(subscribeExpected);
    }

    @Test
    final void getAllToDosTest() {
        when(repository.findAll())
                .thenReturn(Stream.of(subscribeExpected, subscribeExpectedSecond).collect(Collectors.toList()));
        Assertions.assertEquals(2, service.getAll().size());
    }

    @Test
    final void getEmptyListOfToDoTest() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        Assertions.assertEquals(0, service.getAll().size());
    }

}
