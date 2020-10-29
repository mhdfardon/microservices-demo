package com.microservices.demo.services.user.service;

public interface UserService {

    String encrypt(String value);

    String decrypt(String value);

    String getCipherKey();

    String encodeBase64(String encodedStr);

    String decodeBase64(String decodedStr);
}
