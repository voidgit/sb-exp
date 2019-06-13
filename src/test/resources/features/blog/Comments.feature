Feature: Comments
  In order to be sure that my posts are commented by real users
  As a user
  I want to be sure that comments to my posts have email address which is in valid format

  Scenario: comment to user's posts should have email addresses in valid format
    Given 'Samantha' already created some posts
    And other users already commented them
    When user reads comments for the posts
    Then all comments have properly formatted email addresses
