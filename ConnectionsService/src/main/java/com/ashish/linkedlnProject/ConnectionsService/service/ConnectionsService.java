package com.ashish.linkedlnProject.ConnectionsService.service;

import com.ashish.linkedlnProject.ConnectionsService.entity.Person;
import com.ashish.linkedlnProject.ConnectionsService.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionsService {

    private final PersonRepository personRepository;
    public static final Logger log = LoggerFactory.getLogger(ConnectionsService.class);

    public ConnectionsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getFirstDegreeConnectionsOfUser(Long userId){
        log.info("getting first degree connections of user with id:{}",userId);
        return personRepository.getFirstDegreeConnectionsOfUser(userId);

    }
}
