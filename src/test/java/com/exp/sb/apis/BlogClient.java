package com.exp.sb.apis;

import com.exp.sb.apis.datamodel.Comment;
import com.exp.sb.apis.datamodel.Post;
import com.exp.sb.apis.datamodel.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

public class BlogClient {
    private final String baseUri;

    public BlogClient(String baseUri) {
        this.baseUri = baseUri;
    }

    public User getExistingUser(String username) {
        Response response = given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .when().get("users")
                .then().contentType(ContentType.JSON).statusCode(200).extract().response();
        Integer userId = response.jsonPath().getInt("find {it.username == '" + username + "'}.id");
        return new User(userId);
    }

    public List<Post> getPosts(User user) {
        Response response = given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .param("userId", user.getId())
                .when().get("posts")
                .then().contentType(ContentType.JSON).statusCode(200).extract().response();

        List<Integer> postsIds = response.jsonPath().getList("id", Integer.class);
        return postsIds.stream()
                .map(Post::new)
                .collect(Collectors.toList());
    }

    public List<Comment> getCommentsForPosts(List<Post> posts) {
        return posts.stream()
                .map(Post::getPostId)
                .flatMap(this::getCommentsForPost)
                .collect(Collectors.toList());
    }

    private Stream<Comment> getCommentsForPost(Integer postId) {
        Response response = given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .param("postId", postId)
                .when().get("comments")
                .then().contentType(ContentType.JSON).statusCode(200).extract().response();

        return response.jsonPath().getList("email", String.class)
                .stream()
                .map(Comment::new);
    }
}
