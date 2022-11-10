package com.stevancorre.cda.scraper.controllers;

import com.stevancorre.cda.scraper.controllers.files.SendDatabaseController;
import com.stevancorre.cda.scraper.controllers.files.SendEmailController;
import com.stevancorre.cda.scraper.controls.Popup;
import com.stevancorre.cda.scraper.controls.ProviderCheckbox;
import com.stevancorre.cda.scraper.providers.abstraction.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public final class MainController {
    @FXML
    private MenuItem saveMenuItem;
    @FXML
    private MenuItem sendEmailMenuItem;
    @FXML
    private MenuItem sendDbMenuItem;

    @FXML
    private TextField titleInput;
    @FXML
    private ComboBox<Genre> genreInput;
    @FXML
    private DatePicker dateInput;
    @FXML
    private TextField minPriceInput;
    @FXML
    private TextField maxPriceInput;

    @FXML
    private VBox providersContainer;

    @FXML
    private Label progressIndicatorLabel;
    @FXML
    private ProgressBar progressBar;

    @FXML
    private AnchorPane formPane;

    @FXML
    private TextArea resultTextArea;

    private ArrayList<ProviderCheckbox> checkboxes;

    private ArrayList<SearchResult> results;

    @FXML
    public void initialize() {
        dateInput.setConverter(new StringConverter<>() {
            final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy");

            @Override
            public String toString(final LocalDate date) {
                if (date == null) return "";
                return dateFormatter.format(date);
            }

            @Override
            public LocalDate fromString(final String string) {
                if (string == null || string.isEmpty()) return null;
                return LocalDate.parse(string, dateFormatter);
            }
        });

        checkboxes = new ArrayList<>();
        for (final Provider provider : Provider.getProviders()) {
            final ProviderCheckbox checkbox = new ProviderCheckbox(provider);

            providersContainer.getChildren().add(checkbox);
            checkboxes.add(checkbox);
        }

        saveMenuItem.setDisable(true);
        sendEmailMenuItem.setDisable(true);
        sendDbMenuItem.setDisable(true);
        genreInput.getItems().setAll(Genre.values());
    }

    @FXML
    private void onFileSaveMenuClick() {
        // TODO: disable button when result null, same for db export btw
        if (results == null) return;

        final FileChooser chooser = new FileChooser();
        chooser.setTitle("Select file to export scraping results");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt"));

        final File file = chooser.showSaveDialog(titleInput.getScene().getWindow());
        if (file == null) return;

        try {
            final PrintWriter writer = new PrintWriter(file);
            writer.println(getStringResults());
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // TODO: might move to service
        // TODO: display error
        // TODO: display success
    }

    @FXML
    private void onCloseMenuClick() {
        System.out.println(dateInput);

        // TODO: handle unsaved changes
        Platform.exit();
    }

    @FXML
    private void onSendinblueSettingsMenuClick() throws IOException {
        final Popup popup = new Popup("Sendinblue settings", "/fxml/settings/sendinblue-view.fxml", 520, 220);
        popup.show();
    }

    @FXML
    private void onDatabaseSettingsMenuClick() throws IOException {
        final Popup popup = new Popup("Database settings", "/fxml/settings/database-view.fxml", 520, 360);
        popup.show();
    }

    @FXML
    private void onSendAsEmailMenuClick() throws IOException {
        final Popup popup = new Popup(
                "Email sending",
                "/fxml/files/mail-view.fxml",
                520, 160,
                (final SendEmailController controller) -> controller.setResultContent(getStringResults()));
        popup.show();
    }

    @FXML
    private void onSendToDbMenuClick() throws IOException {
        final Popup popup = new Popup(
                "Email sending",
                "/fxml/files/database-view.fxml",
                520, 160,
                (final SendDatabaseController controller) -> controller.setResults(results));
        popup.show();
    }

    @FXML
    private void onSearchButtonClick() {
        interactionsSetDisable(true);

        progressBar.setProgress(0);
        progressIndicatorLabel.setText("Connecting...");

        Double minPrice = null;
        Double maxPrice = null;
        try {
            minPrice = Double.parseDouble(minPriceInput.getText());
            maxPrice = Double.parseDouble(maxPriceInput.getText());
        } catch (final Exception ignored) {
        }

        final Provider[] providers = checkboxes
                .stream()
                .filter(ProviderCheckbox::isSelected)
                .map(ProviderCheckbox::getProvider)
                .toArray(Provider[]::new);

        // please don't read this shit, it works (intellij told me to do that)
        final int[] providersDone = {0};

        results = new ArrayList<>();

        for (final Provider provider : providers) {
            provider.query(
                    new SearchQuery(titleInput.getText(), Genre.Blues, minPrice, maxPrice, null),
                    4,
                    new ProviderCallback() {
                        @Override
                        public void onDone(final SearchResult[] newResults) {
                            results.addAll(Arrays.stream(newResults).toList());

                            if (++providersDone[0] == providers.length) {
                                interactionsSetDisable(false);

                                resultTextArea.setText(getStringResults());
                                formPane.setDisable(false);
                                progressBar.setProgress(1);
                                updateProgressIndicatorLabelText("Done");

                                saveMenuItem.setDisable(false);
                            }
                        }

                        @Override
                        public void onError(final Exception exception) {
                            interactionsSetDisable(false);

                            updateProgressIndicatorLabelText("Error");
                        }

                        @Override
                        public void onNext(final float percentage) {
                            if (providers.length == 1) {
                                progressBar.setProgress(percentage);

                                final int intPercentage = Math.round(percentage * 100);
                                updateProgressIndicatorLabelText(String.format("%d%%", intPercentage));
                            } else {
                                final float percentage1AkaTheWorstName = (float) providersDone[0] / providers.length;

                                progressBar.setProgress(percentage1AkaTheWorstName);

                                final int intPercentage = Math.round(percentage1AkaTheWorstName * 100);
                                updateProgressIndicatorLabelText(String.format("%d%%", intPercentage));
                            }
                        }
                    });
        }
    }

    private void interactionsSetDisable(final boolean disable) {
        formPane.setDisable(disable);
        saveMenuItem.setDisable(disable);
        sendEmailMenuItem.setDisable(disable);
        sendDbMenuItem.setDisable(disable);
    }

    @FXML
    private void onResetFormButtonClick() {
        titleInput.clear();
        genreInput.valueProperty().set(null);
        dateInput.setValue(null);
        minPriceInput.clear();
        maxPriceInput.clear();
    }

    private void updateProgressIndicatorLabelText(final String text) {
        Platform.runLater(() -> {
            progressIndicatorLabel.setText(text);
        });
    }

    private String getStringResults() {
        if (results == null) return "";

        final String delimiter = String.format("\n%s\n", "-".repeat(30));

        return results.stream()
                .map(SearchResult::toString)
                .collect(Collectors.joining(delimiter));
    }
}