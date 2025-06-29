package com.ashish.linkedlnProject.ConnectionsService.consumer;

import com.ashish.linkedlnProject.ConnectionsService.service.PersonService;
import com.ashish.linkedlnProject.userService.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceConsumer {

    private final PersonService personService;

    @KafkaListener(topics = "user_created_topic")
    public void handlePersonCreated(UserCreatedEvent userCreatedEvent ) {
      log.info("handlePersonCreated:{}",userCreatedEvent);
      personService.createPerson(userCreatedEvent.getUserId(),userCreatedEvent.getName());
    }

}
