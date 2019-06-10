package com.exp.sb.apis;

import com.exp.sb.testEnv.EnvironmentVariables;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class Comments {
    private static final Logger logger = LoggerFactory.getLogger(Comments.class);
    private final List<Comment> comments;

    public Comments(Integer postId) {
        Response response = given()
                .baseUri(EnvironmentVariables.getBaseUri())
                .contentType(ContentType.JSON)
                .param("postId", postId)
                .when().get("comments")
                .then().contentType(ContentType.JSON).statusCode(200).extract().response();

//        logger.info("Raw response " + response.asString());

        comments = response.jsonPath().getList("email", String.class)
                .stream()
                .map(Comment::new)
                .collect(Collectors.toList());

//        logger.info("user id: " + id);
    }

    public List<Comment> getComments() {
        return comments;
    }
}
