package com.microservices.demo.services.user.service.impl;

import com.microservices.demo.model.dto.UserDTO;
import com.microservices.demo.model.user.User;
import com.microservices.demo.services.user.security.config.AESCipherConfig;
import com.microservices.demo.services.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.Security;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    AESCipherConfig aesCipherConfig;

    public String encrypt(String value) {
        try {
            SecretKeySpec secretKey = getSecretKey();
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            String encryptedValue = (Base64.encodeBase64String(cipher.doFinal(value.getBytes(Charset.forName("UTF-8")))));
            return encryptedValue;
        } catch (Exception e) {
            log.error("Error while encrypting: " + e.toString());
            return null;
        }
    }

    public String decrypt(String value) {
        try {
            Security.setProperty("crypto.policy", "unlimited");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            cipher.init(Cipher.DECRYPT_MODE, getSecretKey());
            String decryptedValue = new String(cipher.doFinal(Base64.decodeBase64(value)));
            return decryptedValue;
        } catch (Exception e) {
            log.error("Error while encrypting: " + e.toString());
            return null;
        }
    }

    public String getCipherKey() {
        return aesCipherConfig.getAesKey();
    }

    public UserDTO convertUser(User user) {
        String decryptedPassword = decrypt(user.getPassword());
        UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), decryptedPassword, user.getEmail(), user.getRole());
        return userDTO;
    }

    private SecretKeySpec getSecretKey() {
        String cipherKey = getCipherKey();
        SecretKeySpec secretKey = new SecretKeySpec(cipherKey.getBytes(Charset.forName("UTF-8")), "AES");
        return secretKey;
    }

    public String encodeBase64(String encodedStr) {
        return Base64.encodeBase64String(encodedStr.getBytes(Charset.forName("UTF-8")));
    }

    public String decodeBase64(String decodedStr) {
        byte[] decodedByteArr = decodedStr.getBytes(Charset.forName("UTF-8"));
        return new String(Base64.decodeBase64(decodedByteArr));
    }
}
