package com.stevancorre.cda.scraper.controllers.files;

import com.stevancorre.cda.scraper.controls.ErrorAlert;
import com.stevancorre.cda.scraper.controls.SuccessAlert;
import com.stevancorre.cda.scraper.providers.abstraction.SearchResult;
import com.stevancorre.cda.scraper.services.DatabaseService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The controller for interface Files/Send to database
 */
public class SendDatabaseController {
    @FXML
    private Button sendButton;

    private ArrayList<SearchResult> results;

    /**
     * Set the active results
     *
     * @param results The results array list
     */
    public void setResults(final ArrayList<SearchResult> results) {
        this.results = results;
    }

    @FXML
    private void onSendButtonClick() throws IOException {
        // try to send to the db
        // if it fails, display an error alert
        // otherwise display a success alert
        final DatabaseService service = new DatabaseService();
        try {
            service.uploadResults(results.toArray(new SearchResult[0]));

            final Alert alert = new SuccessAlert("Success", "Connection error");
            alert.show();
        } catch (final SQLException ignored) {
            final Alert alert = new ErrorAlert("Error", "Connection error", "Can't connect to the database, please check the connection settings");
            alert.show();
        }
        closeStage();
    }

    @FXML
    private void onCancelButtonClick() {
        closeStage();
    }

    private void closeStage() {
        final Stage stage = (Stage) sendButton.getScene().getWindow();
        stage.close();
    }
}
