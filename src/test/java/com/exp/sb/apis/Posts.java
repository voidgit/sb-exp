package com.exp.sb.apis;

import com.exp.sb.testEnv.EnvironmentVariables;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static io.restassured.RestAssured.given;

public class Posts {
    private static final Logger logger = LoggerFactory.getLogger(Posts.class);
    private final List<Integer> postsIds;

    public Posts(Integer userId) {
        Response response = given()
                .baseUri(EnvironmentVariables.getBaseUri())
                .contentType(ContentType.JSON)
                .param("userId", userId)
                .when().get("posts")
                .then().contentType(ContentType.JSON).statusCode(200).extract().response();

//        logger.info("Raw response " + response.asString());

        postsIds = response.jsonPath().getList("id", Integer.class);
//        logger.info("user id: " + id);
    }

    public List<Integer> getPostsIds() {
        return postsIds;
    }
}
