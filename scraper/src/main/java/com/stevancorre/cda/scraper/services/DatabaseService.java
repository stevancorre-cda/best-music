package com.stevancorre.cda.scraper.services;

import com.google.common.io.Resources;
import com.stevancorre.cda.scraper.providers.abstraction.SearchResult;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
import java.net.URL;
import java.sql.*;

/**
 * The database service, used to make transactions with the db
 */
public final class DatabaseService {
    private static Connection getConnection() throws IOException, SQLException {
        final SettingsService.Settings settings = SettingsService.loadSettings();

        // prepare the connection using the user settings
        return DriverManager.getConnection(
                String.format(
                        "jdbc:mysql://%s:%s/%s",
                        settings.getDbHost(),
                        settings.getDbPort(),
                        settings.getDbName()),
                settings.getDbUserName(),
                settings.getDbUserPassword());
    }

    /**
     * Initialize the database
     */
    public void init() throws IOException, SQLException {
        // run the init.sql script
        final URL url = Resources.getResource("init.sql");
        final ScriptRunner runner = new ScriptRunner(getConnection()) {{
            setLogWriter(null);
        }};
        final Reader reader = new BufferedReader(new FileReader(url.getPath()));

        runner.runScript(reader);
    }

    /**
     * Upload array of results to the database
     *
     * @param results Array of results to upload
     */
    public void uploadResults(final SearchResult[] results) throws SQLException, IOException {
        for (final SearchResult result : results) {
            // send each result one by one
            try (final PreparedStatement statement = getConnection().prepareStatement(
                    "INSERT INTO `result` (`title`, `description`, `price`, `year`, `genreId`) VALUES (?, ?, ?, ?, ?)")) {
                statement.setString(1, result.title());

                statement.setString(2, result.description());

                statement.setDouble(3, result.price());

                if (result.year() != null) statement.setInt(4, result.year());
                else statement.setNull(4, Types.INTEGER);

                if (result.genre() != null) statement.setInt(5, result.genre().getId());
                else statement.setNull(5, Types.INTEGER);

                statement.execute();
            }
        }
    }
}
