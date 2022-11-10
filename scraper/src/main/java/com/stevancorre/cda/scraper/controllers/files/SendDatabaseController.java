package com.stevancorre.cda.scraper.controllers.files;

import com.stevancorre.cda.scraper.providers.abstraction.SearchResult;
import com.stevancorre.cda.scraper.services.DatabaseService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class SendDatabaseController {
    @FXML
    private Button sendButton;

    private ArrayList<SearchResult> results;

    public void setResults(final ArrayList<SearchResult> results) {
        this.results = results;
    }

    @FXML
    private void onSendButtonClick() throws SQLException, IOException {
        final DatabaseService service = new DatabaseService();
        service.uploadResults(results.toArray(new SearchResult[0]));
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
