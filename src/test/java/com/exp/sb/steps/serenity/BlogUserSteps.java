package com.exp.sb.steps.serenity;

import com.exp.sb.apis.Comment;
import com.exp.sb.apis.Comments;
import com.exp.sb.apis.Posts;
import com.exp.sb.apis.User;
import net.thucydides.core.annotations.Step;
import org.apache.commons.validator.routines.EmailValidator;
import org.junit.Rule;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class BlogUserSteps {
    private User user;
    private Posts posts;
    private List<Comment> allCommentsForUsersPosts;

    public void initExistingUser(String username) {
        user = new User(username);
    }

    @Step
    public void fetchPosts() {
        posts = new Posts(user.getId());
    }

    @Step
    public void fetchAllCommentsForPosts() {
        allCommentsForUsersPosts = posts.getPostsIds().stream()
                .map(Comments::new)
                .flatMap(x -> x.getComments().stream())
                .collect(Collectors.toList());
    }

    @Step
    public void verifyThatAllEmailsInCommentsShouldBeValid() {
        List<String> mailAdresses = allCommentsForUsersPosts.stream()
                .map(Comment::getEmailAddress)
                .collect(Collectors.toList());

        // ToDo: remove following artificial failure
        mailAdresses.set(0, mailAdresses.get(0) + "@//enforcing failure");

        EmailValidator emailValidator = EmailValidator.getInstance();
        assertThat(mailAdresses)
                .as("Format of all mail addresses in comments to user's posts should be valid")
                .allSatisfy(email ->
                        assertThat(email)
                                .matches(emailValidator::isValid, "email format should be valid (satisfy email format requirements)")
                );
    }
}