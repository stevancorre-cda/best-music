package com.stevancorre.cda.scraper.controls;

import com.stevancorre.cda.scraper.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * Helper class to open fxml file as a modal
 */
public final class Popup extends Stage {
    /**
     * Constructor
     *
     * @param title  The stage's title
     * @param fxml   The stage's FXML source code
     * @param width  The stage's width
     * @param height The stage's height
     */
    public Popup(final String title, final String fxml, final int width, final int height) throws IOException {
        this(title, fxml, width, height, c -> {
        });
    }

    /**
     * Constructor
     *
     * @param title          The stage's title
     * @param fxml           The stage's FXML source code
     * @param width          The stage's width
     * @param height         The stage's height
     * @param controllerInit Function to initialize controller
     * @param <T>            The controller type
     */
    public <T> Popup(final String title, final String fxml, final int width, final int height, final Consumer<T> controllerInit) throws IOException {
        super();

        // load fxml from disk
        final FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml));
        final Parent root = fxmlLoader.load();

        // initialize controller
        final T controller = fxmlLoader.getController();
        controllerInit.accept(controller);

        final Scene scene = new Scene(root, width, height);

        initModality(Modality.APPLICATION_MODAL);
        setTitle(title);
        setScene(scene);
    }
}
