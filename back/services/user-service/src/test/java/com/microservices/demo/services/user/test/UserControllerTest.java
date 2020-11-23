package com.microservices.demo.services.user.test;

import com.microservices.demo.services.user.UserServiceApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackageClasses = {UserServiceApplication.class})
@Slf4j
public class UserControllerTest {


    @LocalServerPort
    private int port;

    private String uri;

    @PostConstruct
    public void init() {
        uri = "http://localhost:" + port;
    }

    @Test
    public void testReturnAllUsers() {

        // Test Unauthorized Client
        given().auth()
                .preemptive()
                .basic("sophie", "pass1")
                .when()
                .get(uri + "/users/allUsers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());

        List<Map> userResult =
                given().auth()
                        .preemptive()
                        .basic("sophie", "pass")
                        .get(uri + "/users/allUsers")
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(List.class);

        assertThat(userResult.size()).isEqualTo(3);
    }

}
