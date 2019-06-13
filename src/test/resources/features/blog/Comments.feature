Feature: Comments
  In order to be sure that my posts are commented by real users
  As a user
  I want to be sure that comments to my posts have email address which is in valid format

  Scenario: comment to user's posts should have email addresses in valid format
    Given 'Samantha' already created some posts
    And other users already commented them
    When user reads comments for the posts
    Then all comments have properly formatted email addresses


# additional tests given here as examples. not implemented
# in real life there will be more scenarios and they will be distributed throughout different feature files

  Scenario: successfully create new user
    Given user with username 'jane' does not exists
    When user attempts to create new account for username 'jane' for valid email
    Then new user is successfully created

  Scenario: attempt to create user with duplicate username
    Given user with username 'johndoe' exists
    When user attempts to create new account for username 'johndoe' for valid email
    Then user is notified that account with such username already exists

  Scenario: post without a title
    Given logged in user attempts to create a new post
    And this new post does not have a title
    When user submits the post
    Then user is notified that title should not be empty

  Scenario: edit a post
    Given user already submitted a post
    When he edits that post
    Then updates are visible to other users
