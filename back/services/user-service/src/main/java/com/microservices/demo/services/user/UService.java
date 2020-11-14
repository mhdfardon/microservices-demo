package com.microservices.demo.services.user;

import com.microservices.demo.model.User;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.Security;

public class UService {

    public void modifyPass(User user) {
        user.setPassword(decrypt(user.getPassword()));
    }

    public void modifyPass2(Object o) {
        if(o instanceof User) {
            modifyPass((User)o);
        } else {
            o = null;
        }
    }

    public String encrypt(String value) {
        try {
            SecretKeySpec secretKey = getSecretKey();
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            String encryptedValue = (Base64.encodeBase64String(cipher.doFinal(value.getBytes(Charset.forName("UTF-8")))));
            return encryptedValue;
        } catch (Exception e) {
//            log.error("Error while encrypting: " + e.toString());
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
//            log.error("Error while encrypting: " + e.toString());
            return null;
        }
    }

    public String getCipherKey() {
        return "9y$B?E(H+MbQeThWmZq4t7w!z%C*F)J@";
    }

    private SecretKeySpec getSecretKey() {
        String cipherKey = getCipherKey();
        SecretKeySpec secretKey = new SecretKeySpec(cipherKey.getBytes(Charset.forName("UTF-8")), "AES");
        return secretKey;
    }

}
