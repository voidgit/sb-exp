package com.exp.sb.apis.datamodel;

public class Comment {
    private final String emailAddress;

    public Comment(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}
