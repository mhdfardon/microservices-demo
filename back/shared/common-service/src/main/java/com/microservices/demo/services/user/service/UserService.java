package com.microservices.demo.services.user.service;

import com.microservices.demo.model.User;
import com.microservices.demo.model.dto.UserDTO;

public interface UserService {

    String encrypt(String value);

    String decrypt(String value);

    String getCipherKey();

    UserDTO convertUser(User user);

    String encodeBase64(String encodedStr);

    String decodeBase64(String decodedStr);
}
