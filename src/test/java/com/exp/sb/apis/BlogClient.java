package com.exp.sb.apis;

import com.exp.sb.apis.datamodel.Comment;
import com.exp.sb.apis.datamodel.Post;
import com.exp.sb.apis.datamodel.User;
import com.exp.sb.testEnv.EnvironmentVariables;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class BlogClient {
    private static final Logger logger = LoggerFactory.getLogger(BlogClient.class);

    private BlogClient() {
    }

    public static User getExistingUser(String username) {
        Response response = given()
                .baseUri(EnvironmentVariables.getBaseUri())
                .contentType(ContentType.JSON)
                .when().get("users")
                .then().contentType(ContentType.JSON).statusCode(200).extract().response();
//        logger.info("Raw response " + response.asString());
        Integer userId = response.jsonPath().getInt("find {it.username == '" + username + "'}.id");
//        logger.info("user id: " + id);
        return new User(userId);
    }

    public static List<Post> getPosts(User user) {
        Response response = given()
                .baseUri(EnvironmentVariables.getBaseUri())
                .contentType(ContentType.JSON)
                .param("userId", user.getId())
                .when().get("posts")
                .then().contentType(ContentType.JSON).statusCode(200).extract().response();
//        logger.info("Raw response " + response.asString());

        List<Integer> postsIds = response.jsonPath().getList("id", Integer.class);
        return postsIds.stream()
                .map(Post::new)
                .collect(Collectors.toList());
    }

    public static List<Comment> getCommentsForPosts(List<Post> posts) {
        return posts.stream()
                .map(Post::getPostId)
                .flatMap(postId -> {
                    Response response = given()
                            .baseUri(EnvironmentVariables.getBaseUri())
                            .contentType(ContentType.JSON)
                            .param("postId", postId)
                            .when().get("comments")
                            .then().contentType(ContentType.JSON).statusCode(200).extract().response();

//        logger.info("Raw response " + response.asString());
                    return response.jsonPath().getList("email", String.class)
                            .stream()
                            .map(Comment::new);
                }).collect(Collectors.toList());
    }
}
