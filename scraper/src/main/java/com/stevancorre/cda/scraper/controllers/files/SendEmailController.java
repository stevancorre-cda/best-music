package com.stevancorre.cda.scraper.controllers.files;

import com.stevancorre.cda.scraper.controls.ErrorAlert;
import com.stevancorre.cda.scraper.controls.SuccessAlert;
import com.stevancorre.cda.scraper.services.MailService;
import com.stevancorre.cda.scraper.services.SettingsService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sendinblue.ApiException;
import sibModel.CreateSmtpEmail;

import java.io.IOException;

public final class SendEmailController {
    @FXML
    private TextField receiverInput;

    private String resultContent;

    private final MailService mailService;

    public SendEmailController() throws IOException {
        final SettingsService.Settings settings = SettingsService.loadSettings();
        mailService = new MailService(settings.getSibApiKey(), settings.getSibSenderEmail(), settings.getSibSenderName());
    }

    public void setResultContent(final String resultContent) {
        this.resultContent = resultContent;
    }

    @FXML
    private void onSendButtonClick() {
        try {
            mailService.send(receiverInput.getText(), "Scraping results", "Automatic email from BestMusic's scraper", resultContent);

            final Alert alert = new SuccessAlert("Success", "Email sent");
            alert.show();
        } catch (final ApiException ignored) {
            final Alert alert = new ErrorAlert("Error", "Email error", "Invalid receiver email or Sendinblue configuration");
            alert.show();
        }

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
