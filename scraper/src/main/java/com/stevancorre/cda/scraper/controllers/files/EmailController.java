package com.stevancorre.cda.scraper.controllers.files;

import com.stevancorre.cda.scraper.services.MailService;
import com.stevancorre.cda.scraper.services.SettingsService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sendinblue.ApiException;

import java.io.IOException;

public final class EmailController {
    @FXML
    private TextField receiverInput;

    private String resultContent;

    private final MailService mailService;

    public EmailController() throws IOException {
        final SettingsService.Settings settings = SettingsService.loadSettings();
        mailService = new MailService(settings.getSibApiKey(), settings.getSibSenderEmail(), settings.getSibSenderName());
    }

    public void setResultContent(final String resultContent) {
        this.resultContent = resultContent;
    }

    @FXML
    private void onSendButtonClick() throws ApiException {
        // TODO: handle error
        mailService.send(receiverInput.getText(), "Scraping results", "Automatic email from BestMusic's scraper", resultContent);
        closeStage();
    }

    @FXML
    private void onCancelButtonClick() {
        closeStage();
    }

    private void closeStage() {
        final Stage stage = (Stage) receiverInput.getScene().getWindow();
        stage.close();
    }
}
