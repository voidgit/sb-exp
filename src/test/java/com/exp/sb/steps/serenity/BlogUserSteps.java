package com.exp.sb.steps.serenity;

import com.exp.sb.apis.BlogClient;
import com.exp.sb.apis.datamodel.Comment;
import com.exp.sb.apis.datamodel.Post;
import com.exp.sb.apis.datamodel.User;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class BlogUserSteps {
    private User user;
    private List<Post> posts;
    private List<Comment> allCommentsForUsersPosts;
    private BlogClient blogClient;

    public BlogUserSteps() {
        EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
        blogClient = new BlogClient(variables.getProperty("base.uri"));
    }

    public void initExistingUser(String username) {
        user = blogClient.getExistingUser(username);
    }

    @Step
    public void fetchPosts() {
        posts = blogClient.getPosts(user);
        assertThat(posts).hasSizeGreaterThan(0);
    }

    @Step
    public void fetchAllCommentsForPosts() {
        allCommentsForUsersPosts = blogClient.getCommentsForPosts(posts);
        assertThat(allCommentsForUsersPosts).hasSizeGreaterThan(0);
    }

    @Step
    public void verifyThatAllEmailsInCommentsShouldBeValid() {
        List<String> mailAddresses = allCommentsForUsersPosts.stream()
                .map(Comment::getEmailAddress)
                .collect(Collectors.toList());

        EmailValidator emailValidator = EmailValidator.getInstance();
        assertThat(mailAddresses)
                .as("Format of all mail addresses in comments to user's post should be valid")
                .allSatisfy(email ->
                        assertThat(email)
                                .matches(emailValidator::isValid, "email format should be valid (satisfy email format requirements)")
                );
    }
}