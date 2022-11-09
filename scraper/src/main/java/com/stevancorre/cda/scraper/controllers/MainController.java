package com.stevancorre.cda.scraper.controllers;

import com.stevancorre.cda.scraper.controls.ProviderCheckbox;
import com.stevancorre.cda.scraper.providers.DiscogsProvider;
import com.stevancorre.cda.scraper.providers.FnacProvider;
import com.stevancorre.cda.scraper.providers.abstraction.Provider;
import com.stevancorre.cda.scraper.providers.abstraction.ProviderCallback;
import com.stevancorre.cda.scraper.providers.abstraction.SearchResult;
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
    private TextField titleInput;
    @FXML
    private ComboBox<?> genreInput;
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

    private SearchResult[] results;

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
    private void onSearchButtonClick() {
        formPane.setDisable(true);

        progressBar.setProgress(0);
        progressIndicatorLabel.setText("Connecting...");

        final Provider provider = new DiscogsProvider();
        provider.query("elvis", 4, new ProviderCallback() {
            @Override
            public void onDone(final SearchResult[] newResults) {
                results = newResults;

                resultTextArea.setText(getStringResults());
                formPane.setDisable(false);
                updateProgressIndicatorLabelText("Done");

                saveMenuItem.setDisable(false);
            }

            @Override
            public void onError(final Exception exception) {
                formPane.setDisable(false);

                updateProgressIndicatorLabelText("Error");
            }

            @Override
            public void onNext(final float percentage) {
                progressBar.setProgress(percentage);

                final int intPercentage = Math.round(percentage * 100);
                updateProgressIndicatorLabelText(String.format("%d%%", intPercentage));
            }
        });
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

        return Arrays.stream(results)
                .map(SearchResult::toString)
                .collect(Collectors.joining(delimiter));
    }
}