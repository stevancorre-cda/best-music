package com.stevancorre.cda.scraper.controllers.settings;

import com.stevancorre.cda.scraper.services.SettingsService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public final class DatabaseController {
    @FXML
    private TextField dbHostInput;
    @FXML
    private TextField dbPortInput;
    @FXML
    private TextField dbNameInput;
    @FXML
    private TextField dbUserNameInput;
    @FXML
    private TextField dbUserPasswordInput;

    private SettingsService.Settings settings;

    @FXML
    private void initialize() throws IOException {
        settings = SettingsService.loadSettings();

        dbHostInput.setText(settings.getDbHost());
        dbPortInput.setText(settings.getDbPort());
        dbNameInput.setText(settings.getDbName());
        dbUserNameInput.setText(settings.getDbUserName());
        dbUserPasswordInput.setText(settings.getDbUserPassword());
    }

    @FXML
    private void onSaveButtonClick() throws IOException {
        settings.setDbHost(dbHostInput.getText());
        settings.setDbPort(dbPortInput.getText());
        settings.setDbName(dbNameInput.getText());
        settings.setDbUserName(dbUserNameInput.getText());
        settings.setDbUserPassword(dbUserPasswordInput.getText());

        SettingsService.saveSettings(settings);

        closeStage();
    }

    @FXML
    private void onCancelButtonClick() {
        closeStage();
    }

    private void closeStage() {
        final Stage stage = (Stage) dbHostInput.getScene().getWindow();
        stage.close();
    }
}
