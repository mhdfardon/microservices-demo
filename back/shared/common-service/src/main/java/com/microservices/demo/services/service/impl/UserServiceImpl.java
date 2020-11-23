package com.microservices.demo.services.service.impl;

import com.microservices.demo.model.User;
import com.microservices.demo.services.config.AESCipherConfig;
import com.microservices.demo.services.dao.UserRepository;
import com.microservices.demo.services.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.Security;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    AESCipherConfig aesCipherConfig;

    public void decryptUserPassword(User user) {
        user.setPassword(decrypt(user.getPassword()));
    }
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

    @Override
    public Mono<User> decryptUser(Mono<User> user) {
        return user.map( t -> new User(
                t.getUsername(),
                decrypt(t.getPassword()),
                t.getEmail(),
                t.getRole()));
    }
}
