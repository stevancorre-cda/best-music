package com.stevancorre.cda.scraper.providers.abstraction;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Provider base class
 */
public abstract class Provider {
    protected final WebClient client;

    /**
     * Constructor, initializes the webclient
     */
    protected Provider() {
        this.client = new WebClient() {{
            getOptions().setUseInsecureSSL(false);
            getOptions().setCssEnabled(false);
            getOptions().setJavaScriptEnabled(false);
        }};
    }

    /**
     * Get all available providers in the current project
     *
     * @return Array of initialized providers
     */
    public static Provider[] getProviders() {
        return ProvidersReflection.providers.toArray(new Provider[0]);
    }

    /**
     * Starts a thread to start scraping with a given query
     *
     * @param query    The query
     * @param limit    Maximum results count
     * @param callback Callback functions
     */
    public final void query(final SearchQuery query, final int limit, final ProviderCallback callback) {
        // initialize a new thread to avoid blocking the app
        final Thread thread = new Thread(() -> {
            try {
                // initialize scraping
                final ArrayList<SearchResult> results = new ArrayList<>();
                final HtmlPage page = client.getPage(getQueryUrl(query));
                final List<HtmlElement> entries = scrapEntries(page);

                // count entries
                final int entriesCount = Math.min(limit, entries.size());

                int count = 0;
                for (final HtmlElement entry : entries) {
                    if (count++ >= limit) break;

                    callback.onNext((float) count / entriesCount);

                    final SearchResult result = scrapNext(entry);
                    // if price isn't in price range, don't add them to results list
                    if (query.hasPrice() && (result.price() < query.minPrice() || result.price() > query.maxPrice())) {
                        continue;
                    }

                    results.add(scrapNext(entry));
                }

                callback.onDone(results.toArray(new SearchResult[0]));
            } catch (final IOException ex) {
                callback.onError(ex);
            }
        });

        thread.start();
    }

    /**
     * Generate the url for a given query
     *
     * @param query The query
     * @return The formatted url
     */
    protected abstract String getQueryUrl(final SearchQuery query);

    /**
     * Scrap list of entries (ads etc)
     *
     * @param page The source page
     * @return List of elements representing a non scraped search result
     */
    protected abstract List<HtmlElement> scrapEntries(final HtmlPage page);

    /**
     * Scrap a specific search result, called after `scrapEntries`
     *
     * @param source The source html element
     * @return The scraped search result
     */
    protected abstract SearchResult scrapNext(final HtmlElement source) throws IOException;

    /**
     * Starts a thread to start scraping with a given query
     *
     * @param query    The query
     * @param callback Callback functions
     */
    public final void query(final SearchQuery query, final ProviderCallback callback) {
        query(query, Integer.MAX_VALUE, callback);
    }

    /**
     * Get the formatted name of the provider (FnacProvider -> Fnac)
     *
     * @return The provider's name
     */
    public final String getName() {
        return this.getClass().getSimpleName().replace("Provider", "");
    }
}
