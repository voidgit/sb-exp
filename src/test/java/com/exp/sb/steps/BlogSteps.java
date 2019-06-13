package com.exp.sb.steps;

import com.exp.sb.steps.serenity.BlogUserSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

import static org.assertj.core.api.Assertions.assertThat;

public class BlogSteps {

    @Steps
    BlogUserSteps registeredUser;

    @Given("^'([^']+)' already created some posts$")
    public void user_already_created_some_post(String username) {
        registeredUser.initExistingUser(username);
        registeredUser.fetchPosts();
    }

    @Given("^other users already commented them$")
    public void other_user_already_commented_them() {
    }

    @When("^user reads comments for the posts$")
    public void samantha_checks_comments_for_the_posts() {
        registeredUser.fetchAllCommentsForPosts();
    }

    @Then("^all comments have properly formatted email addresses$")
    public void all_comments_have_properly_formatted_email_addresses() {
        registeredUser.verifyThatAllEmailsInCommentsShouldBeValid();
    }
}
