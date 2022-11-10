package com.stevancorre.cda.scraper.controllers.settings;

import com.stevancorre.cda.scraper.services.SettingsService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The controller for the interface Settings/Sendinblue
 */
public final class SendinblueSettingsController {
    @FXML
    private TextField apiKeyInput;

    @FXML
    private TextField senderEmailInput;

    @FXML
    private TextField senderNameInput;

    private SettingsService.Settings settings;

    @FXML
    private void initialize() throws IOException {
        // load user settings from disk and initialize text fields
        settings = SettingsService.loadSettings();

        apiKeyInput.setText(settings.getSibApiKey());
        senderEmailInput.setText(settings.getSibSenderEmail());
        senderNameInput.setText(settings.getSibSenderName());
    }

    @FXML
    private void onSaveButtonClick() throws IOException {
        // save all settings to disk
        settings.setSibApiKey(apiKeyInput.getText());
        settings.setSibSenderEmail(senderEmailInput.getText());
        settings.setSibSenderName(senderNameInput.getText());

        SettingsService.saveSettings(settings);

        closeStage();
    }

    @FXML
    private void onCancelButtonClick() {
        closeStage();
    }

    private void closeStage() {
        final Stage stage = (Stage) apiKeyInput.getScene().getWindow();
        stage.close();
    }
}
