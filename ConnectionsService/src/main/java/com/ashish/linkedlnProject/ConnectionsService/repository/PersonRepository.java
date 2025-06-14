package com.ashish.linkedlnProject.ConnectionsService.repository;

import com.ashish.linkedlnProject.ConnectionsService.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends Neo4jRepository<Person, String> {

    Optional<Person> findByUserId(Long userId);

    @Query("match (personA:Person) -[:CONNECTED_TO]- (personB:Person)"+
            "where personA.userId = 2"+
            "return personB")
    List<Person> getFirstDegreeConnectionsOfUser(Long userId);

}
