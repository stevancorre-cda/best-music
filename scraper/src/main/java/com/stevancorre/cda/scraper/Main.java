package com.stevancorre.cda.scraper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main application
 */
public final class Main extends Application {
    /**
     * Called when the JavaFX application starts
     */
    @Override
    public void start(final Stage stage) throws IOException {
        final FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/stevancorre/cda.scraper/fxml/home-view.fxml"));
        final Scene scene = new Scene(fxmlLoader.load(), 700, 770);
        stage.setTitle("Best Music Scraper");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The application entry point
     */
    public static void main(final String[] args) {
        launch();
    }
}
