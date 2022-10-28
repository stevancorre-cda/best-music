package com.stevancorre.cda.services;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public final class SecretsService {
    public static final String SIB_API_KEY;

    private static final String SECRETS_FILE = "secrets.properties";

    private static final Properties PROPERTIES;

    private SecretsService() {
    }

    static {
        PROPERTIES = new Properties();
        final URL props = ClassLoader.getSystemResource(SECRETS_FILE);

        try {
            PROPERTIES.load(props.openStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        SIB_API_KEY = PROPERTIES.getProperty("SIB_API_KEY");
    }
}
