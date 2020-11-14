package com.microservices.demo.services.service;

import com.microservices.demo.model.User;
import reactor.core.publisher.Mono;

public interface UserService {

    void decryptUserPassword(User User);

    String encrypt(String value);

    String decrypt(String value);

    String getCipherKey();

    String encodeBase64(String encodedStr);

    String decodeBase64(String decodedStr);

    Mono<User> decryptUser(Mono<User> user);
}
