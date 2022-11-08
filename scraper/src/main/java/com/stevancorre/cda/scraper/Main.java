package com.stevancorre.cda.scraper;

import com.stevancorre.cda.scraper.providers.CulturefactoryProvider;
import com.stevancorre.cda.scraper.providers.abstraction.Provider;
import com.stevancorre.cda.scraper.providers.abstraction.ProviderCallback;
import com.stevancorre.cda.scraper.providers.abstraction.SearchResult;
import com.stevancorre.cda.scraper.services.DatabaseService;
import com.stevancorre.cda.scraper.ui.UI;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Arrays;

public class Main {
    public static void main(final String[] args) throws FileNotFoundException, SQLException {
        // final DatabaseService db = new DatabaseService();
        // db.executeScript("init.sql");
        //db.uploadResults(null);

        // final Provider[] providers = Provider.getProviders();
        // System.out.println(Arrays.toString(providers));

        final Provider provider = new CulturefactoryProvider();
        provider.query("elvis", 10, new ProviderCallbackImpl());
        // final UI ui = new UI();
        // ui.start();
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