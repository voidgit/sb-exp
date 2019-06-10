package com.exp.sb.apis;

import com.exp.sb.testEnv.EnvironmentVariables;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

public class User {
    private static final Logger logger = LoggerFactory.getLogger(User.class);

    private Integer id;

    public User(String username) {
        Response response = given()
                .baseUri(EnvironmentVariables.getBaseUri())
                .contentType(ContentType.JSON)
                .when().get("users")
                .then().contentType(ContentType.JSON).statusCode(200).extract().response();

        logger.info("Raw response " + response.asString());

        id = response.jsonPath().getInt("find {it.username == '" + username + "'}.id");
        logger.info("user id: " + id);
    }

    public Integer getId() {
        return id;
    }
}
