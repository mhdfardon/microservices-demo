package com.microservices.demo.services.user;

import com.microservices.demo.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.util.HashMap;
import java.util.Map;

@Slf4j
//@Component
public class DataInitializer {

    private final Map<String, User> users = new HashMap<>();

    public Map<String, User> getUsers() {
        return this.users;
    }

    @EventListener(value = ContextRefreshedEvent.class)
    public void init() {
        initUsers();
    }

    private void initUsers() {
        log.info("start users initialization  ...");
        this.users.clear();
//        this.users.put("1",
//                new User(1, "user01", "John", "john@gmail.com",
//                        true, Arrays.asList("ROLE_USER")));
//        this.users.put("2",
//                new User(2, "user02", "Sophie", "sohpie@gmail.com",
//                        true, Arrays.asList("ROLE_USER", "ROLE_ADMIN")));
//        this.users.put("3",
//                new User(3, "user03", "Julien",
//                        "julien@gmail.com", true, Arrays.asList("ROLE_USER")));
    }
}
