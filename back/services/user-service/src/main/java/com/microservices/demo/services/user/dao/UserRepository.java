package com.microservices.demo.services.user.dao;

import com.microservices.demo.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
