package com.stevancorre.cda.services;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public final class SecretsService {
    public static final String SIB_API_KEY;
    public static final String DB_CONNECTION;
    public static final String DB_USER_NAME;
    public static final String DB_USER_PASSWORD;

    private static final String SECRETS_FILE = "secrets.properties";

    private SecretsService() {
    }

    static {
        final Properties properties = new Properties();
        final URL props = ClassLoader.getSystemResource(SECRETS_FILE);

        try {
            properties.load(props.openStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        SIB_API_KEY = properties.getProperty("SIB_API_KEY");
        DB_CONNECTION = properties.getProperty("DB_CONNECTION");
        DB_USER_NAME = properties.getProperty("DB_USER_NAME");
        DB_USER_PASSWORD = properties.getProperty("DB_USER_PASSWORD");
    }
}
