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
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public final class MainController {
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

                final String delimiter = String.format("\n%s\n", "-".repeat(30));
                final String resultsText =
                        Arrays.stream(results)
                                .map(SearchResult::toString)
                                .collect(Collectors.joining(delimiter));

                resultTextArea.setText(resultsText);
                formPane.setDisable(false);
                updateProgressIndicatorLabelText("Done");
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
}