package com.stevancorre.cda.scraper;

import com.stevancorre.cda.scraper.providers.abstraction.ProviderCallback;
import com.stevancorre.cda.scraper.providers.abstraction.SearchResult;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

public final class Main extends Application {
    @Override
    public void start(final Stage stage) throws IOException {
        final FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/hello-view.fxml"));
        final Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(final String[] args) throws FileNotFoundException, SQLException {
        // final DatabaseService db = new DatabaseService();
        // db.executeScript("init.sql");
        //db.uploadResults(null);

        // final Provider[] providers = Provider.getProviders();
        // System.out.println(Arrays.toString(providers));

        // final Provider provider = new CulturefactoryProvider();
        // provider.query("elvis", 10, new ProviderCallbackImpl());

        launch();
    }
}

final class ProviderCallbackImpl implements ProviderCallback {
    @Override
    public void onNext(final float percentage) {
        System.out.format("%d%%\n", Math.round(percentage * 100));
    }

    @Override
    public void onError(final Exception exception) {
        exception.printStackTrace();
    }

    @Override
    public void onDone(final SearchResult[] results) {
        System.out.println("Done");
        System.out.println(Arrays.toString(results));
    }
}