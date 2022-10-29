package com.stevancorre.cda.providers.abstraction;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Provider {
    protected final WebClient client;

    protected Provider() {
        this.client = new WebClient() {{
            getOptions().setUseInsecureSSL(false);
            getOptions().setCssEnabled(false);
            getOptions().setJavaScriptEnabled(false);
        }};
    }

    public static Provider[] getProviders() {
        return ProvidersReflection.providers.toArray(new Provider[0]);
    }

    public final void query(final String query, final int limit, final ProviderCallback callback) {
        final Thread thread = new Thread(() -> {
            try {
                final ArrayList<SearchResult> results = new ArrayList<>();
                final HtmlPage page = client.getPage(getQueryUrl(query));
                final List<HtmlElement> entries = scrapEntries(page);

                final int entriesCount = Math.min(limit, entries.size());

                int count = 0;
                for (final HtmlElement entry : entries) {
                    if (count++ >= limit) break;

                    results.add(scrapNext(entry));

                    callback.onNext((float) count / entriesCount);
                }

                callback.onDone(results.toArray(new SearchResult[0]));
            } catch (final IOException ex) {
                callback.onError(ex);
            }
        });

        thread.start();
    }

    protected abstract String getQueryUrl(final String query);

    protected abstract List<HtmlElement> scrapEntries(final HtmlPage page);

    protected abstract SearchResult scrapNext(final HtmlElement source) throws IOException;

    public final void query(final String query, final ProviderCallback callback) throws IOException {
        query(query, Integer.MAX_VALUE, callback);
    }

    public final String getName() {
        return this.getClass().getSimpleName().replace("Provider", "");
    }
}
