Feature: Comments
  In order to be sure that my posts are commented by real users
  As a user
  I want to be able to be sure that comments to my posts' email address has valid format

  Scenario: comment to user's posts should have email addresses in valid format
    Given 'Samantha' already created some posts
    And other users already commented them
    When 'Samantha' checks comments for the posts
    Then all comments has propely formatted email addresses