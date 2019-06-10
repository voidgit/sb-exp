package com.exp.sb.testEnv;

public final class EnvironmentVariables {
    private static final String testEnv = System.getProperty("test.env");
    private static final String baseUrl = System.getProperty("base.uri");

    private EnvironmentVariables() {
    }

    public static String getBaseUri() {
        return baseUrl;
    }

    public static String getTestEnv() {
        return testEnv;
    }
}
