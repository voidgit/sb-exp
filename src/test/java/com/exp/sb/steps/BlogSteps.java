package com.exp.sb.steps;

import com.exp.sb.steps.serenity.BlogUserSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

import static org.assertj.core.api.Assertions.assertThat;

public class BlogSteps {

    @Steps
    BlogUserSteps samantha;

    @Given("^'([^']+)' already created some posts$")
    public void samantha_already_created_some_post(String username) {
        samantha.initExistingUser(username);
        samantha.fetchPosts();
    }

    @Given("^other users already commented them$")
    public void other_user_already_commented_them() {
    }

    @When("^'([^']+)' checks comments for the posts$")
    public void samantha_checks_comments_for_the_posts(String username) {
        samantha.fetchAllCommentsForPosts();
    }

    @Then("^all comments has properly formatted email addresses$")
    public void all_comments_has_propely_formatted_email_addresses() {
        samantha.verifyThatAllEmailsInCommentsShouldBeValid();
    }
}
