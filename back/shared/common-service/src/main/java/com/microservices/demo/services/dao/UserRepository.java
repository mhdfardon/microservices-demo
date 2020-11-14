package com.microservices.demo.services.dao;

import com.microservices.demo.model.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

//public interface UserRepository extends JpaRepository<User, Long> {
public interface UserRepository extends ReactiveCrudRepository<User, Long> {

    @Query("select * from user where username = $1")
    Mono<User> findByUsername(String username);
}
