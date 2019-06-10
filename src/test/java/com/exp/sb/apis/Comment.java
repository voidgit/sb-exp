package com.exp.sb.apis;

public class Comment {
    private final String emailAddress;

    public Comment(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}
