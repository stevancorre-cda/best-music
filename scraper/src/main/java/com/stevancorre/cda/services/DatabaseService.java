package com.stevancorre.cda.services;

import com.google.common.io.Resources;
import com.stevancorre.cda.providers.abstraction.SearchResult;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseService {
    private static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    SecretsService.DB_CONNECTION,
                    SecretsService.DB_USER_NAME,
                    SecretsService.DB_USER_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void executeScript(final String name) throws FileNotFoundException {
        final URL url = Resources.getResource(name);
        final ScriptRunner runner = new ScriptRunner(getConnection()) {{
            setLogWriter(null);
        }};
        final Reader reader = new BufferedReader(new FileReader(url.getPath()));

        runner.runScript(reader);
    }

    public void uploadResults(final SearchResult[] results) throws SQLException {
        try (final Statement statement = getConnection().createStatement()) {
            statement.executeUpdate("INSERT INTO `genre` VALUES ('test')");
        }
    }
}
