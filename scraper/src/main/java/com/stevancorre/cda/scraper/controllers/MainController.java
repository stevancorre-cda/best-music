package com.stevancorre.cda.scraper.controllers;

import com.stevancorre.cda.scraper.Main;
import com.stevancorre.cda.scraper.controllers.files.SendDatabaseController;
import com.stevancorre.cda.scraper.controllers.files.SendEmailController;
import com.stevancorre.cda.scraper.controls.ErrorAlert;
import com.stevancorre.cda.scraper.controls.Popup;
import com.stevancorre.cda.scraper.controls.ProviderCheckbox;
import com.stevancorre.cda.scraper.controls.SuccessAlert;
import com.stevancorre.cda.scraper.providers.abstraction.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import org.apache.ibatis.jdbc.Null;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * The main application's controller
 */
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
        // set the date picker format to yyyy (20/11/2022 -> 2022)
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

        // initialize checkbox with available providers
        checkboxes = new ArrayList<>();
        for (final Provider provider : Provider.getProviders()) {
            final ProviderCheckbox checkbox = new ProviderCheckbox(provider);

            providersContainer.getChildren().add(checkbox);
            checkboxes.add(checkbox);
        }

        // initialize genres
        genreInput.getItems().setAll(Genre.values());

        // disable menus by default
        saveMenuItem.setDisable(true);
        sendEmailMenuItem.setDisable(true);
        sendDbMenuItem.setDisable(true);
    }

    @FXML
    private void onFileSaveMenuClick() {
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

            final Alert alert = new SuccessAlert("Success", "File saved");
            alert.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void onCloseMenuClick() {
        System.out.println(dateInput);

        Platform.exit();
    }

    @FXML
    private void onSendinblueSettingsMenuClick() throws IOException {
        final Popup popup = new Popup("Sendinblue settings", "/com/stevancorre/cda.scraper/fxml/settings/sendinblue-view.fxml", 520, 220);
        popup.show();
    }

    @FXML
    private void onDatabaseSettingsMenuClick() throws IOException {
        final Popup popup = new Popup("Database settings", "/com/stevancorre/cda.scraper/fxml/settings/database-view.fxml", 520, 360);
        popup.show();
    }

    @FXML
    private void onSendAsEmailMenuClick() throws IOException {
        final Popup popup = new Popup(
                "Email sending",
                "/com/stevancorre/cda.scraper/fxml/files/mail-view.fxml",
                520, 160,
                (final SendEmailController controller) -> controller.setResultContent(getStringResults()));
        popup.show();
    }

    @FXML
    private void onSendToDbMenuClick() throws IOException {
        final Popup popup = new Popup(
                "Database upload",
                "/com/stevancorre/cda.scraper/fxml/files/database-view.fxml",
                520, 160,
                (final SendDatabaseController controller) -> controller.setResults(results));
        popup.show();
    }

    @FXML
    private void onSearchButtonClick() {
        final Provider[] providers = checkboxes
                .stream()
                .filter(ProviderCheckbox::isSelected)
                .map(ProviderCheckbox::getProvider)
                .toArray(Provider[]::new);

        if (providers.length == 0) {
            final Alert alert = new ErrorAlert("Error", "Query error", "Please select at least one provider");
            alert.show();
            return;
        }

        if (titleInput.getText().isEmpty()) {
            final Alert alert = new ErrorAlert("Error", "Query error", "Please something to search");
            alert.show();
            return;
        }

        // parse prices
        Double minPrice = null;
        Double maxPrice = null;
        try {
            minPrice = Double.parseDouble(minPriceInput.getText());
            maxPrice = Double.parseDouble(maxPriceInput.getText());
        } catch (final Exception ignored) {
            if (!minPriceInput.getText().isEmpty() && !maxPriceInput.getText().isEmpty()) {
                final Alert alert = new ErrorAlert("Error", "Query error", "Invalid price format, please enter doubles");
                alert.show();
                return;
            }
        }

        // pause interactions
        interactionsSetDisable(true);

        progressBar.setProgress(0);
        progressBar.setStyle("-fx-accent: #a0f6fc");
        progressIndicatorLabel.setText("Connecting...");

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

                            // if all providers are done
                            if (++providersDone[0] == providers.length) {
                                // resume interactions
                                interactionsSetDisable(false);

                                resultTextArea.setText(getStringResults());
                                formPane.setDisable(false);

                                // update progress
                                progressBar.setProgress(1);
                                progressBar.setStyle("-fx-accent: #6afe6e");
                                updateProgressIndicatorLabelText(String.format("%d results found", results.size()));

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
                            // if there is only one provider,
                            // use the given percentage
                            // otherwise just use how many scrapers are done on how many there are in total
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

    @FXML
    private void onHelpMenuClick() {
        final URL url2 = Main.class.getResource("/user-guide.pdf");
        System.out.println(url2);
        new Thread(() -> {
            try {
                final URL url = getClass().getResource("/user-guide.pdf");
                Desktop.getDesktop().browse(new URI("file:" + url.getPath()));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void interactionsSetDisable(final boolean disable) {
        // enable or disable interactions
        formPane.setDisable(disable);
        saveMenuItem.setDisable(disable);
        sendEmailMenuItem.setDisable(disable);
        sendDbMenuItem.setDisable(disable);
    }

    @FXML
    private void onResetFormButtonClick() {
        // clear all inputs and checkboxes
        titleInput.clear();
        genreInput.valueProperty().set(null);
        dateInput.setValue(null);
        minPriceInput.clear();
        maxPriceInput.clear();

        for (final ProviderCheckbox checkbox : checkboxes) {
            checkbox.setSelected(false);
        }
    }

    private void updateProgressIndicatorLabelText(final String text) {
        Platform.runLater(() -> {
            progressIndicatorLabel.setText(text);
        });
    }

    private String getStringResults() {
        // generate string for list of results
        if (results == null) return "";

        final String delimiter = String.format("\n%s\n", "-".repeat(30));

        return results.stream()
                .map(SearchResult::toString)
                .collect(Collectors.joining(delimiter));
    }
}