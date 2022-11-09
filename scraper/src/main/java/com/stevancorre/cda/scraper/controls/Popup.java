package com.stevancorre.cda.scraper.controls;

import com.stevancorre.cda.scraper.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public final class Popup extends Stage {
    public Popup(final String title, final String fxml, final int width, final int height) throws IOException {
        super();

        final FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml));
        final Scene scene = new Scene(fxmlLoader.load(), width, height);

        initModality(Modality.APPLICATION_MODAL);
        setTitle(title);
        setScene(scene);
    }
}
