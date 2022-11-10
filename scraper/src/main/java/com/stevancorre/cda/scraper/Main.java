package com.stevancorre.cda.scraper;

import com.stevancorre.cda.scraper.providers.abstraction.Genre;
import com.stevancorre.cda.scraper.providers.abstraction.SearchResult;
import com.stevancorre.cda.scraper.services.DatabaseService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public final class Main extends Application {
    @Override
    public void start(final Stage stage) throws IOException {
        final FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/hello-view.fxml"));
        final Scene scene = new Scene(fxmlLoader.load(), 700, 770);
        stage.setTitle("Best Music Scraper");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(final String[] args) throws SQLException, IOException {
        final SearchResult searchResult = new SearchResult(
                "leboncoin.com",
                null,
                "https://placeoh",
                "title here",
                "descripnio",
                2000,
                Genre.Blues,
                13
        );
        final DatabaseService s = new DatabaseService();
        s.uploadResults(new SearchResult[]{searchResult});

        launch();
    }
}
