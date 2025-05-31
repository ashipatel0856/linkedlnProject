package com.ashish.linkedlnProject.userService.repository;

import com.ashish.linkedlnProject.userService.entity.User;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

//    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
