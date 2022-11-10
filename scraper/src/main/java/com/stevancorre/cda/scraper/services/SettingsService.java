package com.stevancorre.cda.scraper.services;

import java.io.*;
import java.util.Properties;

/**
 * The settings service, used to load and save settings from disk
 */
public final class SettingsService {
    private static final String SettingsPath = "settings.properties";

    private SettingsService() {
    }

    /**
     * Load settings from disk. If no settings was found, initializes and save empty settings
     *
     * @return The settings object
     */
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
                properties.getProperty("SIB_SENDER_NAME"),
                properties.getProperty("DB_SERVER"),
                properties.getProperty("DB_PORT"),
                properties.getProperty("DB_NAME"),
                properties.getProperty("DB_USER_NAME"),
                properties.getProperty("DB_USER_PASSWORD")
        );
    }

    /**
     * Save settings to disk
     *
     * @param settings The settings to save
     */
    public static void saveSettings(final Settings settings) throws IOException {
        final String properties = "" +
                "SIB_API_KEY=" + settings.getSibApiKey() + '\n' +
                "SIB_SENDER_EMAIL=" + settings.getSibSenderEmail() + '\n' +
                "SIB_SENDER_NAME=" + settings.getSibSenderName() + '\n' +
                "DB_SERVER=" + settings.getDbHost() + '\n' +
                "DB_PORT=" + settings.getDbPort() + '\n' +
                "DB_NAME=" + settings.getDbName() + '\n' +
                "DB_USER_NAME=" + settings.getDbUserName() + '\n' +
                "DB_USER_PASSWORD=" + settings.getDbUserPassword() + '\n';

        final File file = new File(SettingsPath);
        final FileWriter writer = new FileWriter(file);
        writer.write(properties);
        writer.close();
    }

    /**
     * Application settings
     */
    public static final class Settings {
        private String sibApiKey;
        private String sibSenderEmail;
        private String sibSenderName;
        private String dbHost;
        private String dbPort;
        private String dbName;
        private String dbUserName;
        private String dbUserPassword;

        /**
         * Constructor
         *
         * @param sibApiKey      The Sendinblue API key
         * @param sibSenderEmail The Sendinblue sender email
         * @param sibSenderName  The Sendinblue sender name
         * @param dbHost         The database host
         * @param dbPort         The database port
         * @param dbName         The database name
         * @param dbUserName     The database user name
         * @param dbUserPassword The database user password
         */
        public Settings(final String sibApiKey,
                        final String sibSenderEmail,
                        final String sibSenderName,
                        final String dbHost,
                        final String dbPort,
                        final String dbName,
                        final String dbUserName,
                        final String dbUserPassword) {
            this.sibApiKey = sibApiKey;
            this.sibSenderEmail = sibSenderEmail;
            this.sibSenderName = sibSenderName;
            this.dbHost = dbHost;
            this.dbPort = dbPort;
            this.dbName = dbName;
            this.dbUserName = dbUserName;
            this.dbUserPassword = dbUserPassword;
        }

        /**
         * Generate empty settings
         *
         * @return Settings object with all fields empty
         */
        public static Settings empty() {
            return new Settings("", "", "", "", "", "", "", "");
        }

        /**
         * Get the Sendinblue API key
         *
         * @return The Sendinblue API key
         */
        public String getSibApiKey() {
            return sibApiKey;
        }

        /**
         * Get the Sendinblue sender email
         *
         * @return The Sendinblue sender email
         */
        public String getSibSenderEmail() {
            return sibSenderEmail;
        }

        /**
         * Get the Sendinblue sender name
         *
         * @return The Sendinblue sender name
         */
        public String getSibSenderName() {
            return sibSenderName;
        }

        /**
         * Get the database host
         *
         * @return The database host
         */
        public String getDbHost() {
            return dbHost;
        }

        /**
         * Get the database port
         *
         * @return The database port
         */
        public String getDbPort() {
            return dbPort;
        }

        /**
         * Get the database name
         *
         * @return The database name
         */
        public String getDbName() {
            return dbName;
        }

        /**
         * Get the database user's name
         *
         * @return The database user's name
         */
        public String getDbUserName() {
            return dbUserName;
        }

        /**
         * Get the database user's password
         *
         * @return The database user's password
         */
        public String getDbUserPassword() {
            return dbUserPassword;
        }

        /**
         * Set the Sendinblue API key
         *
         * @param sibApiKey The API key
         */
        public void setSibApiKey(final String sibApiKey) {
            this.sibApiKey = sibApiKey;
        }

        /**
         * Set the Sendinblue sender email
         *
         * @param sibSenderEmail The Sendinblue sender email
         */
        public void setSibSenderEmail(final String sibSenderEmail) {
            this.sibSenderEmail = sibSenderEmail;
        }

        /**
         * Set the Sendinblue sender name
         *
         * @param sibSenderName The Sendinblue sender name
         */
        public void setSibSenderName(final String sibSenderName) {
            this.sibSenderName = sibSenderName;
        }

        /**
         * Set the database host
         *
         * @param dbHost The database host
         */
        public void setDbHost(final String dbHost) {
            this.dbHost = dbHost;
        }


        /**
         * Set the database port
         *
         * @param dbPort The database port
         */
        public void setDbPort(final String dbPort) {
            this.dbPort = dbPort;
        }

        /**
         * Set the database name
         *
         * @param dbName The database name
         */
        public void setDbName(final String dbName) {
            this.dbName = dbName;
        }

        /**
         * Set the database user's name
         *
         * @param dbUserName The database user's name
         */
        public void setDbUserName(final String dbUserName) {
            this.dbUserName = dbUserName;
        }

        /**
         * Set the database user's password
         *
         * @param dbUserPassword The database user's password
         */
        public void setDbUserPassword(final String dbUserPassword) {
            this.dbUserPassword = dbUserPassword;
        }
    }
}
