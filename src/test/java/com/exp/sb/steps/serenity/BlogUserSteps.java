package com.exp.sb.steps.serenity;

import com.exp.sb.apis.User;

public class BlogUserSteps {
    private User user;

    public void initExistingUser(String username) {
        user = new User(username);
    }
}