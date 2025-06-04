package com.ashish.linkedlnProject.ConnectionsService.controller;

import com.ashish.linkedlnProject.ConnectionsService.entity.Person;
import com.ashish.linkedlnProject.ConnectionsService.service.ConnectionsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
public class ConnectionsController {

    private final ConnectionsService connectionsService;

    public ConnectionsController(ConnectionsService connectionsService) {
        this.connectionsService = connectionsService;
    }

    @GetMapping("/{userId}/first-degree")
    public ResponseEntity<List<Person>> getFirstDegreeConnectionsOfUser(@PathVariable Long userId,
                                                                        @RequestHeader("X-User-Id") Long userIdFromHeader){

        List<Person> personList = connectionsService.getFirstDegreeConnectionsOfUser(userIdFromHeader);
        return ResponseEntity.ok(personList);
    }
}
