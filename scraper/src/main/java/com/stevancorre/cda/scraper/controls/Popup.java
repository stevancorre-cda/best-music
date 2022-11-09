package com.stevancorre.cda.scraper.controls;

import com.stevancorre.cda.scraper.Main;
import com.stevancorre.cda.scraper.controllers.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

public final class Popup extends Stage {
    public Popup(final String title, final String fxml, final int width, final int height) throws IOException {
        this(title, fxml, width, height, c -> {
        });
    }

    public <T> Popup(final String title, final String fxml, final int width, final int height, final Consumer<T> controllerInit) throws IOException {
        super();

        final FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml));
        final Parent root = fxmlLoader.load();
        final T controller = fxmlLoader.getController();
        controllerInit.accept(controller);

        final Scene scene = new Scene(root, width, height);

        initModality(Modality.APPLICATION_MODAL);
        setTitle(title);
        setScene(scene);
    }
}
