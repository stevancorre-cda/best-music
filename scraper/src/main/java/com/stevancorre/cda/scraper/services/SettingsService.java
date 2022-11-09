package com.stevancorre.cda.scraper.services;

import java.io.*;
import java.util.Properties;

public final class SettingsService {
    private static final String SettingsPath = "settings.properties";

    private SettingsService() {
    }

    public static Settings loadSettings() throws IOException {
        final File propsFile = new File(SettingsPath);
        if (!propsFile.exists()) {
            final Settings settings = Settings.empty();
            saveSettings(settings);

            return settings;
        }

        final Properties properties = new Properties();
        final InputStream stream = new FileInputStream(propsFile);
        properties.load(stream);

        return new Settings(
                properties.getProperty("SIB_API_KEY"),
                properties.getProperty("SIB_SENDER_EMAIL"),
                properties.getProperty("DB_SERVER"),
                properties.getProperty("DB_PORT"),
                properties.getProperty("DB_NAME"),
                properties.getProperty("DB_USER_NAME"),
                properties.getProperty("DB_USER_PASSWORD")
        );
    }

    public static void saveSettings(final Settings settings) throws IOException {
        final String properties = "" +
                "SIB_API_KEY=" + settings.getSibApiKey() + '\n' +
                "SIB_SENDER_EMAIL=" + settings.getSibSenderEmail() + '\n' +
                "DB_SERVER=" + settings.getDbServer() + '\n' +
                "DB_PORT=" + settings.getDbPort() + '\n' +
                "DB_NAME=" + settings.getDbName() + '\n' +
                "DB_USER_NAME=" + settings.getDbUserName() + '\n' +
                "DB_USER_PASSWORD=" + settings.getDbUserPassword() + '\n';

        final File file = new File(SettingsPath);
        final FileWriter writer = new FileWriter(file);
        writer.write(properties);
        writer.close();
    }

    public static final class Settings {
        private String sibApiKey;
        private String sibSenderEmail;
        private String dbServer;
        private String dbPort;
        private String dbName;
        private String dbUserName;
        private String dbUserPassword;

        public Settings(String sibApiKey,
                        String sibSenderEmail,
                        String dbServer,
                        String dbPort,
                        String dbName,
                        String dbUserName,
                        String dbUserPassword) {
            this.sibApiKey = sibApiKey;
            this.sibSenderEmail = sibSenderEmail;
            this.dbServer = dbServer;
            this.dbPort = dbPort;
            this.dbName = dbName;
            this.dbUserName = dbUserName;
            this.dbUserPassword = dbUserPassword;
        }

        public static Settings empty() {
            return new Settings("", "", "", "", "", "", "");
        }

        public String getSibApiKey() {
            return sibApiKey;
        }

        public String getSibSenderEmail() {
            return sibSenderEmail;
        }

        public String getDbServer() {
            return dbServer;
        }

        public String getDbPort() {
            return dbPort;
        }

        public String getDbName() {
            return dbName;
        }

        public String getDbUserName() {
            return dbUserName;
        }

        public String getDbUserPassword() {
            return dbUserPassword;
        }

        public void setSibApiKey(final String sibApiKey) {
            this.sibApiKey = sibApiKey;
        }

        public void setSibSenderEmail(final String sibSenderEmail) {
            this.sibSenderEmail = sibSenderEmail;
        }

        public void setDbServer(final String dbServer) {
            this.dbServer = dbServer;
        }

        public void setDbPort(final String dbPort) {
            this.dbPort = dbPort;
        }

        public void setDbName(final String dbName) {
            this.dbName = dbName;
        }

        public void setDbUserName(final String dbUserName) {
            this.dbUserName = dbUserName;
        }

        public void setDbUserPassword(final String dbUserPassword) {
            this.dbUserPassword = dbUserPassword;
        }
    }
}