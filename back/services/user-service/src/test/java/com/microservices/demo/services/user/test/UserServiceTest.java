package com.microservices.demo.services.user.test;

import com.microservices.demo.services.user.UserServiceApplication;
import com.microservices.demo.services.user.security.config.AESCipherConfig;
import com.microservices.demo.services.user.service.UserService;
import com.microservices.demo.services.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {UserServiceApplication.class})
@ComponentScan(basePackageClasses = {UserServiceApplication.class})
@Slf4j
public class UserServiceTest {

    private static final String AES_256_GENERATED_KEY = "QeThWmZq4t7w!z%C*F-JaNdRfUjXn2r5";
    private static final String ENCRYPTED_MPD_AES_256 = "s2/xB1AQ0MzQR/PAAPfcfw==";
    private static final String DECRYPTED_MPD = "D3moadmin/";

    @Mock
    AESCipherConfig aesCipherConfig;

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void userServiceTest() {
        Mockito.when(aesCipherConfig.getAesKey()).thenReturn(AES_256_GENERATED_KEY);

        assertEquals(AES_256_GENERATED_KEY, userService.getCipherKey());

        String encryptedTest = userService.encrypt(DECRYPTED_MPD);
        assertEquals(ENCRYPTED_MPD_AES_256, encryptedTest);

        String decryptedTest = userService.decrypt(encryptedTest);
        assertEquals(DECRYPTED_MPD, decryptedTest);
    }
}
